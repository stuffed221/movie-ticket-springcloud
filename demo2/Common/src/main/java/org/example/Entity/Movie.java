package org.example.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 电影实体类
 */
@TableName("movie")
@ApiModel("电影实体")
public class Movie {

    @TableId(value = "movie_id",type = IdType.AUTO)
    @ApiModelProperty("电影ID")
    private Integer movieId;

    @TableField("movie_name")
    @ApiModelProperty("电影名称")
    private String movieName;

    @TableField("movie_actor")
    @ApiModelProperty("电影演员")
    private String movieActor;

    @TableField("movie_type")
    @ApiModelProperty("电影类型")
    private String movieType;

    @TableField("movie_language")
    @ApiModelProperty("电影语言")
    private String movieLanguage;

    @TableField("movie_time")
    @ApiModelProperty("电影时长(分钟)")
    private Integer movieTime; //电影时长

    @TableField("movie_release_time")
    @ApiModelProperty("上映时间")
    private String releaseTime; //上映时间

    @TableField("movie_introduction")
    @ApiModelProperty("电影简介")
    private String movieIntroduction;

    @TableField("movie_poster")
    @ApiModelProperty("电影海报路径")
    private String moviePoster;

    @ApiModelProperty("创建时间")
    private String movieCreateTime;

    @ApiModelProperty("更新时间")
    private String movieUpdateTime;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieActor() {
        return movieActor;
    }

    public void setMovieActor(String movieActor) {
        this.movieActor = movieActor;
    }

    public String getMovieType() {
        return movieType;
    }

    public void setMovieType(String movieType) {
        this.movieType = movieType;
    }

    public String getMovieLanguage() {
        return movieLanguage;
    }

    public void setMovieLanguage(String movieLanguage) {
        this.movieLanguage = movieLanguage;
    }

    public Integer getMovieTime() {
        return movieTime;
    }

    public void setMovieTime(Integer movieTime) {
        this.movieTime = movieTime;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getMovieIntroduction() {
        return movieIntroduction;
    }

    public void setMovieIntroduction(String movieIntroduction) {
        this.movieIntroduction = movieIntroduction;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getMovieCreateTime() {
        return movieCreateTime;
    }

    public void setMovieCreateTime(String movieCreateTime) {
        this.movieCreateTime = movieCreateTime;
    }

    public String getMovieUpdateTime() {
        return movieUpdateTime;
    }

    public void setMovieUpdateTime(String movieUpdateTime) {
        this.movieUpdateTime = movieUpdateTime;
    }

    public Movie(Integer movieId, String movieName, String movieActor, String movieType, String movieLanguage, Integer movieTime, String releaseTime, String movieIntroduction, String moviePoster, String movieCreateTime, String movieUpdateTime) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieActor = movieActor;
        this.movieType = movieType;
        this.movieLanguage = movieLanguage;
        this.movieTime = movieTime;
        this.releaseTime = releaseTime;
        this.movieIntroduction = movieIntroduction;
        this.moviePoster = moviePoster;
        this.movieCreateTime = movieCreateTime;
        this.movieUpdateTime = movieUpdateTime;
    }

    public Movie() {
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", movieName='" + movieName + '\'' +
                ", movieActor='" + movieActor + '\'' +
                ", movieType='" + movieType + '\'' +
                ", movieLanguage='" + movieLanguage + '\'' +
                ", movieTime=" + movieTime +
                ", releaseTime='" + releaseTime + '\'' +
                ", movieIntroduction='" + movieIntroduction + '\'' +
                ", moviePoster='" + moviePoster + '\'' +
                ", movieCreateTime='" + movieCreateTime + '\'' +
                ", movieUpdateTime='" + movieUpdateTime + '\'' +
                '}';
    }
}
