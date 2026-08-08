package org.example.Mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.Entity.Order;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    Integer countAll();
}
