package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entities.Movies;
import com.app.service.MovieService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
	@Autowired
	private MovieService mservice;
	@PostMapping("/moviestest")
	public ResponseEntity<List<Movies>> getAllMovies() {
        List<Movies> movies = mservice.getAllMoviesAdmin();
        return ResponseEntity.ok(movies);
    }

}
