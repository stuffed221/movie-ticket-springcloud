package org.example.Controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.example.AliOssUtil;
import org.example.Entity.Movie;
import org.example.annotation.OperationLog;
import org.example.HttpResult;
import org.example.Service.MovieService;
import org.example.vo.MovieListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/movie")
@Api(tags = "电影管理接口")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @RequestMapping("/listall")
    @ApiOperation("获取电影列表")
    public HttpResult listAll(){
        return HttpResult.successResult(movieService.findAll());
    }

    @RequestMapping("/list")
    @ApiOperation("获取电影列表")
    public HttpResult list(){
        List<Movie> movies = movieService.findAll();
        List<MovieListVO> voList = movies.stream()
                .map(c -> new MovieListVO(c.getMovieId(), c.getMovieName()))
                .collect(Collectors.toList());
        return HttpResult.successResult(voList);
    }

    @RequestMapping("/page")
    @ApiOperation("分页查询电影")
    public HttpResult page(@ApiParam("当前页码") @RequestParam Integer npage, @ApiParam("查询条件") @RequestBody  Movie movie){
        IPage<Movie> buyIPage = movieService.findByPage(npage, movie);

        if(buyIPage != null){
            return HttpResult.successResult(buyIPage);
        }else {
            return HttpResult.errorResult("查询失败");
        }
    }

    @RequestMapping("/insert")
    @ApiOperation("新增电影")
    @OperationLog("新增电影")
    public HttpResult insert(@ApiParam("电影信息") @RequestBody Movie movie){
        movieService.add(movie);
        return HttpResult.successResult("添加成功");
    }

    @RequestMapping("/update")
    @ApiOperation("更新电影信息")
    @OperationLog("更新电影")
    public HttpResult update(@ApiParam("电影信息") @RequestBody Movie movie){
        movieService.update(movie);
        return HttpResult.successResult("修改成功");
    }

    @RequestMapping("/delete")
    @ApiOperation("删除电影")
    @OperationLog("删除电影")
    public HttpResult delete(@ApiParam("电影ID") Integer id){
        movieService.delete(id);
        return HttpResult.successResult("删除成功");
    }

    @GetMapping("/count")
    @ApiOperation("统计数量")
    public HttpResult count(){
        return HttpResult.successResult(movieService.count());
    }

    @PostMapping("/uploadPoster")
    @ApiOperation("上传电影海报")
    public HttpResult uploadPoster(@RequestParam("file") MultipartFile file,
                                   @RequestParam("movieId") Integer movieId) {
        try {
            String fileName = "poster/" + movieId + "_" + System.currentTimeMillis() + ".jpg";
            String url = AliOssUtil.upload(file.getBytes(), fileName);

            Movie movie = movieService.findById(movieId);
            if (movie == null) {
                return HttpResult.errorResult("影片不存在");
            }
            movie.setMoviePoster(url);
            movieService.update(movie);

            return HttpResult.successResult(url);
        } catch (Exception e) {
            return HttpResult.errorResult("上传失败");
        }
    }

}
