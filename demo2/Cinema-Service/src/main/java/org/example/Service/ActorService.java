package org.example.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.Entity.Actor;

import java.util.List;

public interface ActorService {

    List<Actor> findAll();

    IPage<Actor> findByPage(Integer npage, Actor actor);

    void add(Actor actor);

    Actor findById(Integer id);

    void update(Actor actor);

    void delete(Integer id);


}
