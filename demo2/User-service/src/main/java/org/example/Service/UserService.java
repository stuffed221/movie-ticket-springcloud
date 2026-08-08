package org.example.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.Entity.User;
import org.example.dto.UserLoginDTO;

import java.util.List;

public interface UserService {

    List<User> findAll();

    IPage<User> findByPage(Integer page, User user);

    void add(User user);

    User findById(Integer id);

    void update(User user);

    void delete(Integer id);

    User login(UserLoginDTO dto);

    Integer count();
}
