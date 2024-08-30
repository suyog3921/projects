package com.app.service;

import java.io.IOException; 

import org.springframework.web.multipart.MultipartFile;

import com.app.dto.ApiResponse;

import com.app.entities.Movies;

public interface ImageHandlingService {
	
	ApiResponse uploadImage(Long movieId, MultipartFile image) throws IOException;
	byte[] serveImage(Long movieId) throws IOException;
	//used for uploading img along with movie details
	void uploadImage(Movies movie, MultipartFile image) throws IOException;
}
