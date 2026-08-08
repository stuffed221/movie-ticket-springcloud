package org.example.Service.imp;


import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.Entity.Actor;
import org.example.Mapper.ActorMapper;
import org.example.Service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorServiceI implements ActorService {

    @Autowired
    private ActorMapper actorMapper;

    @Override
    public List<Actor> findAll() {
        return actorMapper.selectList(null);
    }

    @Override
    public IPage<Actor> findByPage(Integer npage, Actor actor) {
        IPage page = new Page(npage, 10);

        QueryWrapper<Actor> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!StringUtils.isEmpty(actor.getActorName()), "actor_name", actor.getActorName());

        return actorMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void add(Actor actor) {
        actorMapper.insert(actor);
        System.out.println("添加成功");
    }

    @Override
    public Actor findById(Integer id) {
        return actorMapper.selectById(id);
    }

    @Override
    public void update(Actor actor) {
        actorMapper.updateById(actor);
        System.out.println("修改成功");
    }

    @Override
    public void delete(Integer id) {
        actorMapper.deleteById(id);
        System.out.println("删除成功");
    }


}
