// BookingRepository.java
package com.app.repository;

import com.app.entities.BookingEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
	List<BookingEntity> findByUserId(Long userId);
}


