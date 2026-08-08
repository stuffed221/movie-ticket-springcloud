package org.example.Mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.Entity.Cinema;

@Mapper
public interface CinemaMapper extends BaseMapper<Cinema> {
}
