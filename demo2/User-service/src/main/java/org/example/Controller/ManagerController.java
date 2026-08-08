package org.example.Controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.example.Entity.Manager;
import org.example.HttpResult;
import org.example.JwtUtils;
import org.example.Service.ManagerService;
import org.example.dto.ManagerLoginDTO;
import org.example.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "管理员接口")
@RestController
@CrossOrigin
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @PostMapping("/list")
    @ApiOperation("查询管理员列表")
    public HttpResult list(@ApiParam("页码") Integer page){
        List<Manager> manager = managerService.findAll();
        if(manager != null){
            return HttpResult.successResult(manager);
        }else {
            return HttpResult.errorResult("查询失败");
        }
    }

    @RequestMapping("/pageLk")
    @ApiOperation("模糊分页查询管理员列表")
    public HttpResult page(@ApiParam("当前页码") @RequestParam Integer npage, @ApiParam("查询条件") @RequestBody Manager manager){
        IPage<Manager> buyIPage = managerService.findByPageLike(npage, manager);
        if(buyIPage != null){
            return HttpResult.successResult(buyIPage);
        }else {
            return HttpResult.errorResult("查询失败");
        }
    }


    /*
    对于管理员的精确查询，用于登录
     */
    @RequestMapping("/pageEq")
    @ApiOperation("精确分页查询管理员列表")
    public HttpResult pageEq(@ApiParam("当前页码")  Integer npage, @ApiParam("查询条件") Manager manager){
        IPage<Manager> buyIPage = managerService.findByPageEq(npage, manager);
        if(buyIPage != null){
            return HttpResult.successResult(buyIPage);
        }else {
            return HttpResult.errorResult("失败");
        }
    }

    @RequestMapping("/insert")
    @ApiOperation("添加管理员")
    public HttpResult insert(@ApiParam("管理员信息") @RequestBody Manager manager){
        managerService.add(manager);
        return HttpResult.successResult("添加成功");
    }

    @RequestMapping("/update")
    @ApiOperation("修改管理员")
    public HttpResult update(@ApiParam("管理员信息") @RequestBody Manager manager){
        managerService.update(manager);
        return HttpResult.successResult("修改成功");

    }

    @RequestMapping("/delete")
    @ApiOperation("注销管理员")
    public HttpResult delete(@ApiParam("管理员ID") @RequestParam Integer id){
        managerService.delete(id);
        return HttpResult.successResult("注销成功");
    }

    @PostMapping("/login")
    @ApiOperation("管理员登录")
    public HttpResult login(@ApiParam("登录信息") @Validated @RequestBody ManagerLoginDTO dto) {
        Manager manager = managerService.login(dto);
        String token = JwtUtils.createToken(manager.getManagerId(), manager.getManagerStatus());
        LoginVO loginVO = new LoginVO(token, manager.getManagerId(), manager.getManagerStatus(),manager.getManagerName());
        manager.setManagerStatus("1");
        managerService.update(manager);
        return HttpResult.successResult(loginVO);
    }

    @PostMapping("/logout")
    @ApiOperation("管理员退出")
    public HttpResult logout(@ApiParam("返回id") @RequestParam Integer managerId) {
        Manager manager = managerService.findById(managerId);
        manager.setManagerStatus("0");
        managerService.update(manager);
        return HttpResult.successResult("退出成功");
    }

    @RequestMapping("/idsearch")
    @ApiOperation("id分页查询")
    public HttpResult idsearch(@ApiParam("id") @RequestParam Integer id){
        System.out.println("按id查询");

        Manager manager= managerService.findById(id);
        List<Manager> managerList = new ArrayList<>();
        managerList.add(manager);
        return HttpResult.successResult(managerList);
    }
}
