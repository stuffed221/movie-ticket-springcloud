package org.example.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.Entity.Manager;
import org.example.dto.ManagerLoginDTO;

import java.util.List;

public interface ManagerService {

    List<Manager> findAll();

    IPage<Manager> findByPageLike(Integer page, Manager manager);

    IPage<Manager> findByPageEq(Integer page, Manager manager);

    void add(Manager manager);

    Manager findById(Integer id);

    void update(Manager manager);

    void delete(Integer id);

    Manager login(ManagerLoginDTO dto);
}
