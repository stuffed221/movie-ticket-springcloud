package org.example.Mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.Entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
