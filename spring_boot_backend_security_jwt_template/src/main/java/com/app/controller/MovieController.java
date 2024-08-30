package com.app.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.MovieDTO;
import com.app.service.ImageHandlingService;
import com.app.service.MovieService;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:3000")
public class MovieController {
	@Autowired
	private MovieService mservice;
	@Autowired
	private ImageHandlingService imageService;
	
	
	// 6. Upload image for existing emp
	// http://host:port/employees/images/{empId} , method=POST
	@PostMapping(value = "/images/{movieId}",
			consumes = "multipart/form-data")
	public ResponseEntity<?> uploadImage(@PathVariable 
			Long movieId, 
			@RequestParam MultipartFile image)
			throws IOException {
		System.out.println("in upload image " + movieId);
		return ResponseEntity.status(HttpStatus.CREATED).
				body(imageService.uploadImage(movieId, image));
	}
	
	
//
////7. download image
//	// http://host:port/employees/images/{empId} , method=GET
//	@GetMapping(value = "/images/{empId}", 
//			produces = 
//		{ IMAGE_GIF_VALUE, IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE })
//	public ResponseEntity<?> downloadImage(@PathVariable long empId) throws IOException {
//		System.out.println("in download image " + empId);
//		return ResponseEntity.ok(imageService.serveImage(empId));
//	}
//
//	// 8. Pagination demo
//	// Get all emps , paginated
//	// http://host:port/employees , method=GET
//	// req params : pageNumber , def val 0 , optional
//	// pageSize : def val 3 , optional
//
//	@GetMapping
//	public ResponseEntity<?> getAllEmpsPaginated(
//			@RequestParam(defaultValue = "0", required = false) int pageNumber,
//			@RequestParam(defaultValue = "3", required = false) int pageSize) {
//		System.out.println("in get all emps " + pageNumber + " " + pageSize);
//		List<EmployeeDTO> list = employeeService.getAllEmployees(pageNumber, pageSize);
//		if (list.isEmpty())
//			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//		// emps found
//		return ResponseEntity.ok(list);
//	}
//
	// 9. Upload emp details along with image
	// http://host:port/employees/images , method=POST
	@PostMapping(value = "/movie_images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadMovieAndImage(@RequestPart("image") MultipartFile image, @RequestPart("movieDto") MovieDTO movieDto)
			throws IOException {
//		System.out.println("Content Type of image: " + image.getContentType());
//	    System.out.println("Content Type of movieDto: " + movieDto);
		System.out.println("in upload movie details n image " + movieDto + " " + image);	
//		 if (image != null) {
//		        System.out.println("Image received: " + image.getOriginalFilename());
//		    } else {
//		        System.out.println("Image part is null");
//		    }
//		    if (movieDto != null) {
//		        System.out.println("MovieDTO received: " + movieDto);
//		    } else {
//		        System.out.println("MovieDTO part is null");
//		    }
		return ResponseEntity.
				status(HttpStatus.CREATED) 
				.body(mservice.addNewMovieWithImage(movieDto, image));
	}
	
	// TEST file upload TEST
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadImage(@RequestPart("image") MultipartFile image) throws IOException {
	    if (image != null) {
	        System.out.println("Image received: " + image.getOriginalFilename());
	        return ResponseEntity.status(HttpStatus.CREATED).body("Image uploaded successfully");
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image part is null");
	    }
	}

	
	
}
