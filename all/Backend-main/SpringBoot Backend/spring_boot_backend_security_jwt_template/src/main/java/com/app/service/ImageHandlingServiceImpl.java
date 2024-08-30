package com.app.service;

import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.commons.io.FileUtils.writeByteArrayToFile;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.custom_exceptions.ApiException;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.ApiResponse;
import com.app.entities.Movies;
import com.app.repository.MovieRepository;

@Service
@Transactional
public class ImageHandlingServiceImpl implements ImageHandlingService {
	
	
	// injecting value of the field read from applicatoin.properties file
	@Value("${file.upload.location}") // field level DI , <property name n value />
	// ${file.upload.location} SpEL :Spring expr language
	private String uploadFolder;

	@Autowired
	private MovieRepository movieRepo;

	@PostConstruct
	public void init() throws IOException {
		// chk if folder exists --yes --continue
		File folder = new File(uploadFolder);
		if (folder.exists()) {
			System.out.println("folder exists alrdy !");
		} else {
			// no --create a folder
			folder.mkdir();
			System.out.println("created a folder !");
		}
	}

	@Override
	public ApiResponse uploadImage(Long movieId, MultipartFile image) throws IOException {
		// get emp from emp id
		Movies movie = movieRepo.
				findById(movieId).orElseThrow(() -> new ResourceNotFoundException("Invalid movie ID!!!!"));
		// emp found --> PERSISTENT
		// store the image on server side folder
		String path = uploadFolder.concat(image.getOriginalFilename());
		System.out.println(path);
		// Use FileUtils method : writeByte[] --> File
		writeByteArrayToFile(new File(path), image.getBytes());
		// set image path in DB (emps table)
		movie.setImagePath(path);
		// OR to store the img directly in DB as a BLOB
		// emp.setImage(image.getBytes());
		return new ApiResponse("Image file uploaded successfully for emp id " + movieId);
	}

	@Override
	public void uploadImage(Movies movie, MultipartFile image) throws IOException {
		// store the image on server side folder
		String path = uploadFolder.concat(image.getOriginalFilename());
		System.out.println(path);
		// Use FileUtils method : writeByte[] --> File
		writeByteArrayToFile(new File(path), image.getBytes());
		// set image path
		movie.setImagePath(path);
		// OR to store the img directly in DB as a BLOB
		// emp.setImage(image.getBytes());
		System.out.println("Image file uploaded successfully for emp " + movie.getMName());
	}

	@Override
	public byte[] serveImage(Long movieId) throws IOException {
		// get emp by id
		Movies movie = movieRepo.findById(movieId).orElseThrow(() -> new ResourceNotFoundException("Invalid emp ID!!!!"));
		// emp found --> PERSISTENT
		String path = movie.getImagePath();
		if (path != null) {
			// path ---> File --> byte[]
			return readFileToByteArray(new File(path));
			// OR from DB : return emp.getImage();
		} else
			throw new ApiException("Image not yet assigned !!!!");

	}

}
