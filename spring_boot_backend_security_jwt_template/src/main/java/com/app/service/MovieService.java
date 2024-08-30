package com.app.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.MovieDTO;
import com.app.entities.Movies;


public interface MovieService {

	
	MovieDTO addNewMovieWithImage(MovieDTO dto, MultipartFile image) throws IOException;
	
	MovieDTO get(Long movieId);

	MovieDTO update(MovieDTO movieDto, Long movieId);

	List<MovieDTO> getAllMovies(int pageNumber, int pageSize);
	
	List<MovieDTO> getMoviesCount();
//	MovieDTO update(MovieDTO movieDto, Long movieId)

	long getTotalMoviesCount();

	List<Movies> getAllMoviesAdmin();

	Movies addMovie(Movies movie);

	Movies updateMovie(Long movieId, Movies updatedMovie);

	void deleteMovie(Long movieId);
}
