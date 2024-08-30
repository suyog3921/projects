package com.app.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.MovieDTO;
import com.app.entities.Movies;
import com.app.repository.MovieRepository;


@Service
@Transactional
public class MovieServiceImpl implements MovieService {

	@Autowired
	private ModelMapper mapper;
	@Autowired
	private MovieRepository movieRepo;
	@Autowired
	private ImageHandlingService imgHandlingService;

	
	@Override
	public MovieDTO addNewMovieWithImage(MovieDTO dto, MultipartFile image) throws IOException {
		// validate confirm password
//				if (dto.getPassword().equals(dto.getConfirmPassword())) {
					// validate dept id
					//Department dept = deptRepo.findById(dto.getDeptId())
//							.orElseThrow(() -> new ResourceNotFoundException("Invalid Dept Id!!!"));
					Movies movie = mapper.map(dto, Movies.class);
					// upload image , set image path to emp.
					imgHandlingService.uploadImage(movie, image);
					//dept.addEmployee(empEntity);
					Movies savedMovie = movieRepo.save(movie);// Actually not needed by hibernate BUT to get persistent emp id
																// n to send to clnt doing this !
					// System.out.println("emp entity id " + empEntity.getId() + " " +
					// savedEmp.getId());

					return mapper.map(savedMovie, MovieDTO.class);

				
//				throw new ApiException("Passwords don't match");
	}


	 @Override
	    public MovieDTO get(Long movieId) {
	        Movies movie = movieRepo.findById(movieId).orElseThrow(() -> new ResourceNotFoundException("Movie not found of given Id !!"));
	        
	        return mapper.map(movie, MovieDTO.class);
	    }
	 
	 @Override
	    public MovieDTO update(MovieDTO movieDto, Long movieId) {

	        //fetch the Movie of given id
	        Movies Movie = movieRepo.findById(movieId).orElseThrow(() -> new ResourceNotFoundException("Movie not found of given Id !!"));
	        System.out.println("movie dto name"+ Movie.toString());
	        Movie.setMName(movieDto.getMName());
//	        System.out.println("movie name"+Movie.getMName()+);
	        Movie.setMDescription(movieDto.getMDescription());
	        Movie.setMRating(movieDto.getMRating());
//	      
	        Movie.setMovieImageName(movieDto.getMovieImageName());
	        System.out.println("movie name"+ Movie.toString());

//	        save the entity
	        Movies updatedMovie = movieRepo.save(Movie);
	        return mapper.map(updatedMovie, MovieDTO.class);
	    }
	 
	 
	 
	 
	 @Override
		public List<MovieDTO> getAllMovies(int pageNumber, int pageSize) {
			// Creates a PageRequest(imple class of Pageable : i/f for pagination)
			// based upon page no n size
			Pageable pageable = PageRequest.of(pageNumber, pageSize);
			// fetches the Page of Emps --> getContent() --> List<Emp>
			List<Movies> movieList = movieRepo.findAll(pageable).getContent();
			
			return movieList.
					stream() //Stream<Emp>
					//Stream i/f method - map(Function mapperFunction)
					.map(emp -> mapper.map(emp, MovieDTO.class)) //Stream<dto>
					.collect(Collectors.toList());
		}
	 
	 
	 public List<MovieDTO> getMoviesCount(){
		 
		 return movieRepo.findAll().stream() //Stream<Emp>
					//Stream i/f method - map(Function mapperFunction)
					.map(emp -> mapper.map(emp, MovieDTO.class)) //Stream<dto>
					.collect(Collectors.toList());
		 
	 }

// Paginated Response
	@Override
	public long getTotalMoviesCount() {
		 return movieRepo.count();
	}
	 
//	 @Override
//	    public MovieDTO get(Long movieId) {
//	        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found of given Id !!"));
//	        return mapper.map(product, ProductDto.class);
//	    }
	 
	@Override
	public List<Movies> getAllMoviesAdmin() {
        return movieRepo.findByIsDeletedFalse();
    }
	@Override
    public Movies addMovie(Movies movie) {
        return movieRepo.save(movie);
    }
	 
	@Override
    public Movies updateMovie(Long movieId, Movies updatedMovie) {
        Movies existingMovie = movieRepo.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        existingMovie.setMName(updatedMovie.getMName());
        existingMovie.setMDescription(updatedMovie.getMDescription());
        existingMovie.setMRating(updatedMovie.getMRating());
        existingMovie.setImagePath(updatedMovie.getImagePath());
        existingMovie.setMovieImageName(updatedMovie.getMovieImageName());
        return movieRepo.save(existingMovie);
    }
	@Override
    public void deleteMovie(Long movieId) {
        Movies movie = movieRepo.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        movie.setIsDeleted(true);
        movieRepo.save(movie);
    }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	

}
