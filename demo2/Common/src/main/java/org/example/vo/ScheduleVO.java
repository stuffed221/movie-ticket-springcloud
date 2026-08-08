package org.example.vo;

public class ScheduleVO {
    private Integer scheduleId;
    private Integer scheduleMovieId;
    private String scheduleMovieName;
    private Integer scheduleCinemaId;
    private String scheduleCinemaName;
    private Integer scheduleNo;
    private Double schedulePrice;
    private String movieType;
    private String movieLanguage;
    private Integer movieTime;
    private String scheduleCreateTime;

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

    public Integer getScheduleNo() {
        return scheduleNo;
    }

    public void setScheduleNo(Integer scheduleNo) {
        this.scheduleNo = scheduleNo;
    }

    public Double getSchedulePrice() {
        return schedulePrice;
    }

    public void setSchedulePrice(Double schedulePrice) {
        this.schedulePrice = schedulePrice;
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

    public String getScheduleCreateTime() {
        return scheduleCreateTime;
    }

    public void setScheduleCreateTime(String scheduleCreateTime) {
        this.scheduleCreateTime = scheduleCreateTime;
    }
}
