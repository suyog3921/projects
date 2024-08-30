package com.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dto.ApiResponse;
import com.app.dto.SigninRequest;
import com.app.dto.SigninResponse;
import com.app.dto.Signup;
import com.app.entities.UserEntity;
@Service
@Transactional
public interface UserService {
	//add signup method
	
	Signup userRegistration(Signup signup);
	
	List<UserEntity> getAllUser();
	
	SigninResponse authenticateUser(SigninRequest dto);
	
	public SigninResponse findByemailservice(SigninRequest dto);
}
