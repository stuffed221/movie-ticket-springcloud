package org.example.vo;

public class CinemaListVO {

    private Integer cinemaId;

    private String cinemaName;

    public CinemaListVO() {
    }

    public CinemaListVO(Integer cinemaId, String cinemaName) {
        this.cinemaId = cinemaId;
        this.cinemaName = cinemaName;
    }

    public Integer getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    @Override
    public String toString() {
        return "CinemaListVO{" +
                "cinemaId=" + cinemaId +
                ", cinemaName='" + cinemaName + '\'' +
                '}';
    }
}
