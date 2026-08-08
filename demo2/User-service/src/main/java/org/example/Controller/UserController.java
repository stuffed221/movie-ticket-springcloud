package org.example.Controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.example.Entity.User;
import org.example.HttpResult;
import org.example.JwtUtils;
import org.example.Service.UserService;
import org.example.dto.UserLoginDTO;
import org.example.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
@Api(tags = "用户管理接口")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    @ApiOperation("获取用户列表")
    public HttpResult list(@ApiParam("页码") Integer page){
        List<User> users = userService.findAll();

        if(users != null){
            return HttpResult.successResult(users);
        }else {
            return HttpResult.errorResult("查询失败");
        }
    }

    @RequestMapping("/page")
    @ApiOperation("分页查询用户")
    public HttpResult page(@ApiParam("当前页码") @RequestParam Integer npage, @ApiParam("查询条件") User user){
        IPage<User> buyIPage = userService.findByPage(npage, user);

        if(buyIPage != null){
            return HttpResult.successResult(buyIPage);
        }else {
            return HttpResult.errorResult("查询失败");
        }
    }

    @RequestMapping("/insert")
    @ApiOperation("新增用户")
    public HttpResult insert(@ApiParam("用户信息") @RequestBody User user){
        userService.add(user);
        return HttpResult.successResult("添加成功");
    }

    @RequestMapping("/update")
    @ApiOperation("更新用户信息")
    public HttpResult update(@ApiParam("用户信息") @RequestBody User user){
        userService.update(user);
        return HttpResult.successResult("修改成功");
    }

    @RequestMapping("/delete")
    @ApiOperation("删除用户")
    public HttpResult delete(@ApiParam("用户ID") Integer id){
        userService.delete(id);
        return HttpResult.successResult("删除成功");
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public HttpResult login(@ApiParam("登录信息") @Validated @RequestBody UserLoginDTO dto) {
        User user = userService.login(dto);
        String token = JwtUtils.createToken(user.getUserId(), "user");
        LoginVO loginVO = new LoginVO(token, user.getUserId(), "user",user.getUserName());
        return HttpResult.successResult(loginVO);
    }

    @GetMapping("/count")
    @ApiOperation("统计数量")
    public HttpResult count(){
        return HttpResult.successResult(userService.count());
    }
}
