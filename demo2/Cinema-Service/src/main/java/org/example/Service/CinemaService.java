package org.example.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.Entity.Cinema;

import java.util.List;

public interface CinemaService {
    
    List<Cinema> findAll();

    IPage<Cinema> findByPage(Integer page, Cinema cinema);

    void add(Cinema cinema);

    Cinema findById(Integer id);

    void update(Cinema cinema);

    void delete(Integer id);

}
