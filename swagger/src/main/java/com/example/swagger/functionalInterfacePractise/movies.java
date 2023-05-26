package com.example.swagger.functionalInterfacePractise;

public class movies {

    private String moviesName;
    private String actorName;
    private Long releaseYear;

    public movies(String moviesName, String actorName, Long releaseYear) {
        this.moviesName = moviesName;
        this.actorName = actorName;
        this.releaseYear = releaseYear;
    }

    public String getMoviesName() {
        return moviesName;
    }

    public void setMoviesName(String moviesName) {
        this.moviesName = moviesName;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public Long getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Long releaseYear) {
        this.releaseYear = releaseYear;
    }
}
