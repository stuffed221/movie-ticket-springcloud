package org.example.Service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.Entity.User;
import org.example.annotation.AutoFill;
import org.example.enums.OperationType;
import org.example.Mapper.UserMapper;
import org.example.Service.UserService;
import org.example.constant.MessageConstant;
import org.example.dto.UserLoginDTO;
import org.example.exception.LoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceI implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return userMapper.selectList(null);
    }

    @Override
    public IPage<User> findByPage(Integer npage, User user) {
        IPage page = new Page(npage,2);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(!StringUtils.isEmpty(user.getUserName()),"user_name", user.getUserName());
        return userMapper.selectPage(page,queryWrapper);


    }

    @Override
    @AutoFill(OperationType.INSERT)
    public void add(User user) {
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        userMapper.insert(user);
    }

    @Override
    public User login(UserLoginDTO dto) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("user_name", dto.getUserName());
        User user = userMapper.selectOne(qw);
        if (user == null) {
            throw new LoginException(MessageConstant.ACCOUNT_NOT_EXIST);
        }
        if (!passwordEncoder.matches(dto.getUserPassword(), user.getUserPassword())) {
            throw new LoginException(MessageConstant.LOGIN_FAILED);
        }
        if (user.getUserStatus() == null || user.getUserStatus() == 1) {
            throw new LoginException(MessageConstant.ACCOUNT_LOCKED);
        }
        user.setUserPassword(null);
        return user;
    }

    @Override
    public User findById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    @AutoFill(OperationType.UPDATE)
    public void update(User user) {


        userMapper.updateById(user);
    }

    @Override
    public Integer count(){


        return (userMapper.selectCount(null));
    }


    @Override
    public void delete(Integer integer) {


        userMapper.deleteById(integer);
    }
}
