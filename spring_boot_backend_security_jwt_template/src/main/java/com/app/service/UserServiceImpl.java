package com.app.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exceptions.AuthenticationException;
import com.app.dto.ApiResponse;
import com.app.dto.SigninRequest;
import com.app.dto.SigninResponse;
import com.app.dto.Signup;
import com.app.entities.UserEntity;
import com.app.repository.UserEntityRepository;
import com.app.security.JwtUtils;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserEntityRepository userRepository;
	@Autowired
	private EmailSenderService senderService;
	@Autowired
	private ModelMapper mapper;
	@Autowired
    private JwtUtils jwtUtils;

	@Autowired
	private PasswordEncoder encoder;
	@Autowired
    private AuthenticationManager authMgr;
	
	
	public void sendRegistrationConfirmation(UserEntity user) {
	    String email = user.getEmail();
	    String subject = "Welcome to MovieMagic!";
	    String body = String.format(
	            "<html>" +
	            "<body style='font-family: Arial, sans-serif; color: #333;'>" +
	            "<div style='max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px; background-color: #f9f9f9;'>" +
	            "<h2 style='color: #2a2a2a;'>Welcome to MovieMagic, %s %s!</h2>" +
	            "<p style='font-size: 16px;'>Thank you for registering on <strong>MovieMagic</strong>! We're thrilled to have you join our movie booking community.</p>" +
	            "<p style='font-size: 16px;'>You can now explore the latest movies, book your favorite seats, and enjoy a fantastic cinema experience.</p>" +
	            "<p style='font-size: 16px;'>If you have any questions, feel free to contact our support team. We’re here to help you!</p>" +
	            "<p style='font-size: 16px;'>Best regards,<br/>The MovieMagic Team</p>" +
	            "<footer style='margin-top: 20px; padding-top: 10px; border-top: 1px solid #ddd; font-size: 12px; color: #888;'>MovieMagic Inc.,Pune <br/></footer>" +
	            "</div>" +
	            "</body>" +
	            "</html>",
	            user.getFirstName(),
	            user.getLastName()
	        );

	    senderService.sendHtmlEmail(email, subject, body);
	}
	@Override
	public Signup userRegistration(Signup reqDTO) {
		//dto --> entity
		UserEntity user=mapper.map(reqDTO, UserEntity.class);
		user.setPassword(encoder.encode(user.getPassword()));//pwd : encrypted using SHA
//		senderService.sendSimpleEmail(user.getEmail(),
//				"This is email body",
//				"This is email subject");
		sendRegistrationConfirmation(user);
		return mapper.map(userRepository.save(user), Signup.class);
	}


	@Override
	public List<UserEntity> getAllUser() {


		return userRepository.findAll();
	}

	

	@Override
	public SigninResponse authenticateUser(SigninRequest dto) {
		// 1.invoke dao 's method
		UserEntity user = userRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword())
				.orElseThrow(() -> 
				new AuthenticationException("Fail"));
		//valid login -user : persistent -> entity -> dto
		SigninResponse sir =  mapper.map(user, SigninResponse.class);
		sir.setMessage("success");
		return sir;
		
//		Authentication authentication = authMgr.authenticate(
//		        new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
//		    );
//		    SecurityContextHolder.getContext().setAuthentication(authentication);
//		    String jwt = jwtUtils.generateJwtToken(authentication);
//		    return new SigninResponse(jwt, "Successful Auth!!!!");
//	}
	
//	 try {
//	        Authentication authentication = authMgr.authenticate(
//	            new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
//	        );
//	        SecurityContextHolder.getContext().setAuthentication(authentication);
//	        String jwt = jwtUtils.generateJwtToken(authentication);
//	        return new SigninResponse(jwt, "Successful Auth!!!!");
//	    } catch (BadCredentialsException e) {
//	        // Log the exception details
//	        System.out.println("Authentication failed: " + e.getMessage());
//	        throw e;
//	    }
	 
	}


	@Override
	public SigninResponse findByemailservice(SigninRequest dto) {
		UserEntity user = userRepository.findByEmail(dto.getEmail())
				.orElseThrow(() -> 
				new AuthenticationException("Fail"));
		//valid login -user : persistent -> entity -> dto
		SigninResponse sir =  mapper.map(user, SigninResponse.class);
		sir.setMessage("success");
		return sir;
	}


}
