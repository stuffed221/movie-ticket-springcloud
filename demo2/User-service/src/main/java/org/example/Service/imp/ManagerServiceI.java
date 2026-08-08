package org.example.Service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.DateUtils;
import org.example.Entity.Manager;
import org.example.Mapper.ManagerMapper;
import org.example.Service.ManagerService;
import org.example.constant.MessageConstant;
import org.example.annotation.AutoFill;
import org.example.enums.OperationType;
import org.example.dto.ManagerLoginDTO;
import org.example.exception.LoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ManagerServiceI implements ManagerService {

    @Autowired
    private ManagerMapper managerMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 查询所有管理员信息
     *
     * @return
     */
    @Override
    public List<Manager> findAll() {
        return managerMapper.selectList(null);
    }

    /**
     * 分页查询管理员信息
     *
     * @param npage
     * @param manager
     * @return
     */
    @Override
    public IPage<Manager> findByPageLike(Integer npage, Manager manager) {
        IPage page = new Page(npage, 6);

        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();

        queryWrapper.like(!StringUtils.isEmpty(manager.getManagerName()), "manager_name", manager.getManagerName());
        queryWrapper.like(!StringUtils.isEmpty(manager.getManagerPassword()), "manager_password", manager.getManagerPassword());
        queryWrapper.eq(!StringUtils.isEmpty(manager.getManagerSex()), "manager_sex", manager.getManagerSex());
        return managerMapper.selectPage(page, queryWrapper);


    }

    @Override
    public IPage<Manager> findByPageEq(Integer npage, Manager manager) {
        IPage page = new Page(npage, 3);

        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(!StringUtils.isEmpty(manager.getManagerName()), "manager_name", manager.getManagerName());
        queryWrapper.eq(!StringUtils.isEmpty(manager.getManagerPassword()), "manager_password", manager.getManagerPassword());
        return managerMapper.selectPage(page, queryWrapper);


    }

    /**
     * 添加管理员信息
     *
     * @param manager
     */
    @Override
    @AutoFill(OperationType.INSERT)
    public void add(Manager manager) {
        manager.setManagerPassword(passwordEncoder.encode(manager.getManagerPassword()));
        if (manager.getManagerSex() == null || manager.getManagerSex().isEmpty()) {
            manager.setManagerSex("男");
        }
        if (manager.getManagerStatus() == null || manager.getManagerStatus().isEmpty()) {
            manager.setManagerStatus("0");
        }
        managerMapper.insert(manager);
    }

    @Override
    public Manager login(ManagerLoginDTO dto) {
        QueryWrapper<Manager> qw = new QueryWrapper<>();
        qw.eq("manager_name", dto.getManagerName());
        Manager manager = managerMapper.selectOne(qw);
        if (manager == null) {
            throw new LoginException(MessageConstant.ACCOUNT_NOT_EXIST);
        }
        if (!passwordEncoder.matches(dto.getManagerPassword(), manager.getManagerPassword())) {
            throw new LoginException(MessageConstant.LOGIN_FAILED);
        }
        manager.setManagerPassword(null);
        return manager;
    }




    /**
     * 根据id查询管理员信息
     *
     * @param id
     * @return
     */
    @Override
    public Manager findById(Integer id) {
        return managerMapper.selectById(id);
    }

    /**
     * 修改管理员信息
     *
     * @param manager
     */
    @Override
    @AutoFill(OperationType.UPDATE)
    public void update(Manager manager) {

        managerMapper.updateById(manager);
    }


    /**
     * 按id删除管理员信息
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        managerMapper.deleteById(id);
    }
}
