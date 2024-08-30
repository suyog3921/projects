package com.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.UserEntity;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
//derived finder 
//	Optional<UserEntity> findByEmail(String email);
	
	Optional<UserEntity> findByEmailAndPassword(String email,String pass);
	
	Optional<UserEntity> findByEmail(String email);

}
