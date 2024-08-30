package com.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.ImageResponse;
import com.app.dto.MovieDTO;
import com.app.dto.PaginatedMoviesResponse;
import com.app.entities.Movies;
import com.app.service.FileService;
import com.app.service.MovieService;

@RestController
@RequestMapping("/moviestest")
@CrossOrigin(origins = "http://localhost:3000")
public class MoviesImageTestController {
	@Autowired
	private MovieService mservice;
	@Autowired
    private FileService fileService;

    @Value("${product.image.path}")
    private String imagePath;
    
  // upload image
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/image/{movieId}")
    public ResponseEntity<ImageResponse> uploadMovieImage(
            @PathVariable Long movieId,
            @RequestParam("movieImage") MultipartFile image
    ) throws IOException {
        String fileName = fileService.uploadFile(image, imagePath);
        MovieDTO movieDto = mservice.get(movieId);
        movieDto.setMovieImageName(fileName);
        MovieDTO updatedProduct = mservice.update(movieDto,movieId);
        ImageResponse response = ImageResponse.builder().imageName(updatedProduct.getMovieImageName()).message("Movie image is successfully uploaded !!").status(HttpStatus.CREATED).success(true).build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }


    // get or serve image

    @GetMapping(value = "/image/{productId}")
    public void serveUserImage(@PathVariable Long productId, HttpServletResponse response) throws IOException {
    	MovieDTO movieDto = mservice.get(productId);
        InputStream resource = fileService.getResource(imagePath, movieDto.getMovieImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

    }
    
   
 	@GetMapping(value = "/pagenumber/{pageNumber}")
 	public ResponseEntity<?> getAllEmpsPaginated(
 	        @PathVariable(required = false) int pageNumber,
 	        @RequestParam(defaultValue = "4", required = false) int pageSize) {

 	    System.out.println("in get all emps " + pageNumber + " " + pageSize);

 	    // Get paginated list of movies
 	    List<MovieDTO> list = mservice.getAllMovies(pageNumber, pageSize);

 	    // Get total count of movies
 	    long totalMovies = mservice.getTotalMoviesCount();

 	    if (list.isEmpty())
 	        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

 	    // Return both list of movies and total count
 	    PaginatedMoviesResponse response = new PaginatedMoviesResponse(list, totalMovies);
 	    return ResponseEntity.ok(response);
 	}
 	
 	
 	// get movies count
 	@GetMapping
 	(value="/totalsize")
 	public ResponseEntity<?> getMoviesCount(
// 			@RequestParam(defaultValue = "0", required = false) int pageNumber,
 			) {
 		
 		List<MovieDTO> list = mservice.getMoviesCount();
 		if (list.isEmpty())
 			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
 		// emps found
 		return ResponseEntity.ok(list.size());
 	}
 	
 	
 	// get movie by id
 	
 	@GetMapping("/{movieId}")
    public ResponseEntity<MovieDTO> getMovies(@PathVariable Long movieId) {
 		MovieDTO moviedto = mservice.get(movieId);
        return new ResponseEntity<>(moviedto, HttpStatus.OK);
    }
    
 	@GetMapping
//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Movies>> getAllMovies() {
        List<Movies> movies = mservice.getAllMoviesAdmin();
        return ResponseEntity.ok(movies);
    }

    @PostMapping("/add")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Movies> addMovie(@RequestBody Movies movie) {
        Movies addedMovie = mservice.addMovie(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedMovie);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Movies> updateMovie(@PathVariable Long id, @RequestBody Movies movie) {
        Movies updatedMovie = mservice.updateMovie(id, movie);
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
    	mservice.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
    
}
