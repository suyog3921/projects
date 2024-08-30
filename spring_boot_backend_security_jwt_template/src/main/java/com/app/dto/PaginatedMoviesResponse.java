package com.app.dto;

import java.util.List;

import lombok.Data;


@Data
public class PaginatedMoviesResponse {
    private List<MovieDTO> movies;
    private long totalMovies;

    public PaginatedMoviesResponse(List<MovieDTO> movies, long totalMovies) {
        this.movies = movies;
        this.totalMovies = totalMovies;
    }

       
}
