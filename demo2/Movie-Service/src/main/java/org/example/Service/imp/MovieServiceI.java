package org.example.Service.imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.DateUtils;
import org.example.Entity.Movie;
import org.example.Mapper.MovieMapper;
import org.example.Service.MovieService;
import org.example.annotation.AutoFill;
import org.example.enums.OperationType;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class MovieServiceI implements MovieService {

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public List<Movie> findAll() {
        String key = "movie:all";
        String cached = redisTemplate.opsForValue().get(key);
        if (cached != null) {
            return JSON.parseArray(cached, Movie.class);
        }
        RLock lock = redissonClient.getLock("lock:" + key);
        try {
            if (lock.tryLock(3, 30, TimeUnit.SECONDS)) {
                try {
                    cached = redisTemplate.opsForValue().get(key);
                    if (cached != null) {
                        return JSON.parseArray(cached, Movie.class);
                    }
                    List<Movie> list = movieMapper.selectList(null);
                    int ttl = 30 * 60 + new Random().nextInt(60);
                    redisTemplate.opsForValue().set(key, JSON.toJSONString(list), ttl, TimeUnit.SECONDS);
                    return list;
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return movieMapper.selectList(null);
    }

    @Override
    public IPage<Movie> findByPage(Integer npage, Movie movie) {
        String key = "movie:page:" + npage + ":name=" + movie.getMovieName()
                + ":type=" + movie.getMovieType() + ":actor=" + movie.getMovieActor()
                + ":lang=" + movie.getMovieLanguage();

        String cached = redisTemplate.opsForValue().get(key);
        if (cached != null) {
            return JSON.parseObject(cached, new TypeReference<Page<Movie>>() {});
        }

        RLock lock = redissonClient.getLock("lock:" + key);
        try {
            if (lock.tryLock(3, 30, TimeUnit.SECONDS)) {
                try {
                    cached = redisTemplate.opsForValue().get(key);
                    if (cached != null) {
                        return JSON.parseObject(cached, new TypeReference<Page<Movie>>() {});
                    }

                    IPage page = new Page(npage, 10);
                    QueryWrapper<Movie> queryWrapper = new QueryWrapper<>();
                    queryWrapper.like(!StringUtils.isEmpty(movie.getMovieName()), "movie_name", movie.getMovieName());
                    queryWrapper.like(!StringUtils.isEmpty(movie.getMovieType()), "movie_type", movie.getMovieType());
                    queryWrapper.like(!StringUtils.isEmpty(movie.getMovieActor()), "movie_actor", movie.getMovieActor());
                    queryWrapper.eq(!StringUtils.isEmpty(movie.getMovieLanguage()), "movie_language", movie.getMovieLanguage());
                    IPage<Movie> result = movieMapper.selectPage(page, queryWrapper);

                    int ttl = 30 * 60 + new Random().nextInt(60);
                    redisTemplate.opsForValue().set(key, JSON.toJSONString(result), ttl, TimeUnit.SECONDS);
                    return result;
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();//如果之前try被中断了，恢复中断标记
        }
        return movieMapper.selectPage(new Page(npage, 10), new QueryWrapper<>());
    }

    @Override
    @AutoFill(OperationType.INSERT)
    public void add(Movie movie) {
        movieMapper.insert(movie);
        flushMovieCache();
    }

    @Override
    public Movie findById(Integer id) {
        return movieMapper.selectById(id);
    }

    @Override
    @AutoFill(OperationType.UPDATE)
    public void update(Movie movie) {
        movieMapper.updateById(movie);
        flushMovieCache();
    }

    @Override
    public void delete(Integer id) {
        movieMapper.deleteById(id);
        flushMovieCache();
    }

    @Override
    public Integer count() {
        return movieMapper.selectCount(null);
    }

    private void flushMovieCache() {
        Set<String> keys = redisTemplate.keys("movie:page:*");
        if (keys != null) keys.forEach(k -> redisTemplate.delete(k));
        redisTemplate.delete("movie:all");
    }
}
