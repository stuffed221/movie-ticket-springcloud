package org.example.Service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.Entity.Cinema;
import org.example.Mapper.CinemaMapper;
import org.example.Service.CinemaService;
import org.example.annotation.AutoFill;
import org.example.enums.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaServiceI implements CinemaService {

    @Autowired
    private CinemaMapper cinemaMapper;

    @Override
    public List<Cinema> findAll() {
        return cinemaMapper.selectList(null);
    }

    @Override
    public IPage<Cinema> findByPage(Integer npage, Cinema cinema) {
        IPage page = new Page(npage, 10);

        QueryWrapper<Cinema> queryWrapper = new QueryWrapper<>();

        queryWrapper.like(!StringUtils.isEmpty(cinema.getCinemaName()), "cinema_name", cinema.getCinemaName());
        queryWrapper.like(!StringUtils.isEmpty(cinema.getCinemaAddress()), "cinema_address", cinema.getCinemaAddress());
        return cinemaMapper.selectPage(page, queryWrapper);
    }

    @Override
    @AutoFill(OperationType.INSERT)
    public void add(Cinema cinema) {
        cinemaMapper.insert(cinema);
    }

    @Override
    public Cinema findById(Integer id) {
        return cinemaMapper.selectById(id);
    }

    @Override
    @AutoFill(OperationType.UPDATE)
    public void update(Cinema cinema) {


        cinemaMapper.updateById(cinema);
    }

    @Override
    public void delete(Integer id) {
        cinemaMapper.deleteById(id);
    }


}
