package org.example.vo;

public class MovieListVO {

    private Integer movieId;

    private String movieName;

    public MovieListVO(Integer movieId, String movieName) {
        this.movieId = movieId;
        this.movieName = movieName;
    }

    public MovieListVO() {
    }

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

    @Override
    public String toString() {
        return "MovieListVO{" +
                "movieId=" + movieId +
                ", movieName='" + movieName + '\'' +
                '}';
    }
}
