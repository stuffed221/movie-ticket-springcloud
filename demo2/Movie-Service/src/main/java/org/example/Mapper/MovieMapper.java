package org.example.Mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.Entity.Movie;

@Mapper
public interface MovieMapper extends BaseMapper<Movie> {
}
