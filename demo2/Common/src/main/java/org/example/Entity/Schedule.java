package org.example.Entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@TableName("schedule")
@ApiModel("排片实体")
public class Schedule {

    @TableId(value = "schedule_id",type = IdType.AUTO)
    @ApiModelProperty("排片ID")
    private Integer scheduleId;
    @TableField("schedule_movie_id")
    @ApiModelProperty("电影ID")
    private Integer scheduleMovieId;
    @ApiModelProperty("电影名称")
    private String scheduleMovieName;
    @TableField("schedule_cinema_id")
    @ApiModelProperty("影院ID")
    private Integer scheduleCinemaId;
    @ApiModelProperty("影院名称")
    private String scheduleCinemaName;
    @ApiModelProperty("排片影厅号")
    private Integer scheduleHall;
    @TableField("schedule_price")
    @ApiModelProperty("票价")
    private Integer schedulePrice;
    @ApiModelProperty("放映时间")
    private String scheduleShowTime;
    @TableField("schedule_remainseats")
    @ApiModelProperty("剩余座位")
    private Integer scheduleRemainseats;
    @ApiModelProperty("创建时间")
    private String scheduleCreateTime;
    @ApiModelProperty("更新时间")
    private String scheduleUpdateTime;

    public Schedule() {
    }

    public Schedule(Integer scheduleId, Integer scheduleMovieId, String scheduleMovieName, Integer scheduleCinemaId, String scheduleCinemaName, Integer scheduleHall, Integer schedulePrice, String scheduleShowTime, Integer scheduleRemainseats, String scheduleCreateTime, String scheduleUpdateTime) {
        this.scheduleId = scheduleId;
        this.scheduleMovieId = scheduleMovieId;
        this.scheduleMovieName = scheduleMovieName;
        this.scheduleCinemaId = scheduleCinemaId;
        this.scheduleCinemaName = scheduleCinemaName;
        this.scheduleHall = scheduleHall;
        this.schedulePrice = schedulePrice;
        this.scheduleShowTime = scheduleShowTime;
        this.scheduleRemainseats = scheduleRemainseats;
        this.scheduleCreateTime = scheduleCreateTime;
        this.scheduleUpdateTime = scheduleUpdateTime;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleId=" + scheduleId +
                ", scheduleMovieId=" + scheduleMovieId +
                ", scheduleMovieName='" + scheduleMovieName + '\'' +
                ", scheduleCinemaId=" + scheduleCinemaId +
                ", scheduleCinemaName='" + scheduleCinemaName + '\'' +
                ", scheduleHall=" + scheduleHall +
                ", schedulePrice=" + schedulePrice +
                ", scheduleShowTime='" + scheduleShowTime + '\'' +
                ", scheduleRemainseats=" + scheduleRemainseats +
                ", scheduleCreateTime='" + scheduleCreateTime + '\'' +
                ", scheduleUpdateTime='" + scheduleUpdateTime + '\'' +
                '}';
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Integer getScheduleMovieId() {
        return scheduleMovieId;
    }

    public void setScheduleMovieId(Integer scheduleMovieId) {
        this.scheduleMovieId = scheduleMovieId;
    }

    public String getScheduleMovieName() {
        return scheduleMovieName;
    }

    public void setScheduleMovieName(String scheduleMovieName) {
        this.scheduleMovieName = scheduleMovieName;
    }

    public Integer getScheduleCinemaId() {
        return scheduleCinemaId;
    }

    public void setScheduleCinemaId(Integer scheduleCinemaId) {
        this.scheduleCinemaId = scheduleCinemaId;
    }

    public String getScheduleCinemaName() {
        return scheduleCinemaName;
    }

    public void setScheduleCinemaName(String scheduleCinemaName) {
        this.scheduleCinemaName = scheduleCinemaName;
    }

    public Integer getScheduleHall() {
        return scheduleHall;
    }

    public void setScheduleHall(Integer scheduleHall) {
        this.scheduleHall = scheduleHall;
    }

    public Integer getSchedulePrice() {
        return schedulePrice;
    }

    public void setSchedulePrice(Integer schedulePrice) {
        this.schedulePrice = schedulePrice;
    }

    public String getScheduleShowTime() {
        return scheduleShowTime;
    }

    public void setScheduleShowTime(String scheduleShowTime) {
        this.scheduleShowTime = scheduleShowTime;
    }

    public Integer getScheduleRemainseats() {
        return scheduleRemainseats;
    }

    public void setScheduleRemainseats(Integer scheduleRemainseats) {
        this.scheduleRemainseats = scheduleRemainseats;
    }

    public String getScheduleCreateTime() {
        return scheduleCreateTime;
    }

    public void setScheduleCreateTime(String scheduleCreateTime) {
        this.scheduleCreateTime = scheduleCreateTime;
    }

    public String getScheduleUpdateTime() {
        return scheduleUpdateTime;
    }

    public void setScheduleUpdateTime(String scheduleUpdateTime) {
        this.scheduleUpdateTime = scheduleUpdateTime;
    }
}
