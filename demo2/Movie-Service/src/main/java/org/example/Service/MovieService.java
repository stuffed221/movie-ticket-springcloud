package org.example.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.Entity.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> findAll();

    IPage<Movie> findByPage(Integer page, Movie movie);

    void add(Movie movie);

    Movie findById(Integer id);

    void update(Movie movie);

    void delete(Integer id);

    Integer count();
}
