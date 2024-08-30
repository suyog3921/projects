package com.app.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.Movies;

@Repository
public interface MovieRepository extends JpaRepository<Movies, Long> {
//	List<Movie> findByDeptId(long movieId);
	
	List<Movies> findByIsDeletedFalse();
}
