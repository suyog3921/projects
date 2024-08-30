package com.app.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exceptions.AuthenticationException;
import com.app.dto.ApiResponse;
import com.app.dto.SigninRequest;
import com.app.dto.SigninResponse;
import com.app.dto.Signup;
import com.app.entities.UserEntity;
import com.app.repository.UserEntityRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserEntityRepository userRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	
	@Override
	public ApiResponse userRegistration(Signup signup) {
		UserEntity user = mapper.map(signup, UserEntity.class);
	
		userRepository.save(user);
		UserEntity user1=new UserEntity();
		
		return new ApiResponse("User Registered Successfully" );
	}


	@Override
	public List<UserEntity> getAllUser() {
		
		
		return userRepository.findAll();
	}


	@Override
	public SigninResponse authenticateUser(SigninRequest dto) {
		// 1.invoke dao 's method
		UserEntity user = userRepository.findByEmailAndPassword(
						dto.getEmail(), dto.getPassword())
						.orElseThrow(() -> 
						new AuthenticationException("Invalid Email or Password !!!!!!"));
				//valid login -user : persistent -> entity -> dto
				return mapper.map(user, SigninResponse.class);
	}
	

}
