package org.example.Controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.example.Entity.Cinema;
import org.example.HttpResult;
import org.example.Service.CinemaService;
import org.example.vo.CinemaListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Api(tags = "影院管理接口")
@RestController
@CrossOrigin
@RequestMapping("/cinema")
public class CinemaController {

    @Autowired
    private CinemaService cinemaService;

    @ApiOperation("获取影院列表")
    @RequestMapping("/list")
    public HttpResult list(){
        List<Cinema> cinemas = cinemaService.findAll();
        List<CinemaListVO> voList = cinemas.stream()
                .map(c -> new CinemaListVO(c.getCinemaId(), c.getCinemaName()))
                .collect(Collectors.toList());
        return HttpResult.successResult(voList);
    }

    @ApiOperation("分页查询影院")
    @RequestMapping("/page")
    public HttpResult page(@ApiParam("当前页码") @RequestParam Integer npage, @ApiParam("查询条件") @RequestBody Cinema cinema){
        IPage<Cinema> buyIPage = cinemaService.findByPage(npage, cinema);

        if(buyIPage != null){
            return HttpResult.successResult(buyIPage);
        }else {
            return HttpResult.errorResult("查询失败");
        }


    }

    
    @ApiOperation("新增影院")
    @RequestMapping("/insert")
    public HttpResult insert(@ApiParam("影院信息") @RequestBody Cinema cinema){
        cinemaService.add(cinema);
        return HttpResult.successResult("添加成功");
    }


    @ApiOperation("更新影院信息")
    @RequestMapping("/update")
    public HttpResult update(@ApiParam("影院信息") @RequestBody Cinema cinema){
        cinemaService.update(cinema);
        return HttpResult.successResult("修改成功");
    }

    @ApiOperation("删除影院")
    @RequestMapping("/delete")
    public HttpResult delete(@ApiParam("影院ID") @RequestBody Integer id){
        cinemaService.delete(id);
        return HttpResult.successResult("删除成功");
    }

}
