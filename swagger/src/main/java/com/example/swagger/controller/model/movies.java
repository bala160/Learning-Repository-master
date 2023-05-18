package com.example.swagger.controller.model;

public class movies {

    public String actorame;
    public String movieName;
    public int releaseYear;
    public int rating;

    public movies(String actorame, String movieName, int releaseYear, int rating) {
        this.movieName = movieName;
        this.actorame = actorame;
        this.releaseYear = releaseYear;
        this.rating = rating;
    }

    public String getActorame() {
        return actorame;
    }

    public void setActorame(String actorame) {
        this.actorame = actorame;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
