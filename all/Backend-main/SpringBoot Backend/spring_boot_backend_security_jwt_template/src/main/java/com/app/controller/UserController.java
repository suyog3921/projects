package com.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.custom_exceptions.AuthenticationException;
import com.app.dto.ApiResponse;
import com.app.dto.SigninRequest;
import com.app.dto.SigninResponse;
import com.app.dto.Signup;
import com.app.entities.UserEntity;
import com.app.security.JwtUtils;
import com.app.service.UserService;

@RestController
@RequestMapping("/users")
@Validated
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private AuthenticationManager authMgr;
	
//	@PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<ApiResponse> signup(@RequestBody Signup signup) {
//		ApiResponse response = userService.userRegistration(signup);
//		return ResponseEntity.status(HttpStatus.CREATED).body(response);
//	}

	@PostMapping("/signup")
	public ResponseEntity<?> userSignup(@RequestBody @Valid Signup dto) {
		System.out.println("in sign up " + dto);  
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.userRegistration(dto));
	}
	
	@GetMapping(value = "/getUsers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserEntity>> getUsers() {
		List<UserEntity> users = userService.getAllUser();
		return ResponseEntity.ok(users);
	}



//	@PostMapping("/signin") 
//	public ResponseEntity<?> signInUser(
//			@RequestBody @Valid SigninRequest request) {
//		System.out.println("in signin " + request);
//		System.out.println("service "+userService);
//		return ResponseEntity.ok(
//				userService.authenticateUser(request));
//		
//	}
	
	/*
	 * URL - http://host:port/users/signin Method - POST request payload : Auth req
	 * DTO : email n password resp payload : In case of success : Auth Resp DTO :
	 * mesg + JWT token + SC 201 In case of failure : SC 401
	 * 
	 */
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody 
			@Valid SigninRequest request) {
		System.out.println("in sign in" + request);
		//create a token(implementation of Authentication i/f)
		//to store un verified user email n pwd
		UsernamePasswordAuthenticationToken token=new 
				UsernamePasswordAuthenticationToken(request.getEmail(), 
						request.getPassword());
		//invoke auth mgr's authenticate method;
		Authentication verifiedToken = authMgr.authenticate(token);
		//=> authentication n authorization  successful !
		System.out.println(verifiedToken.getPrincipal().getClass());//custom user details object
		//create JWT n send it to the clnt in response
		SigninResponse sir=userService.findByemailservice(request);
		SigninResponse resp=new SigninResponse
				(jwtUtils.generateJwtToken(verifiedToken),sir.getId(),sir.getFirstName(),sir.getLastName(),sir.getEmail(),sir.getRole(),
				"Successful Auth!!!!");
		
//		resp.setFirstName(sir.getFirstName());
//		resp.setLastName(sir.getFirstName());
//		resp.setRole(sir.getRole());
//		resp.setEmail(sir.getEmail());
//		resp.setId(sir.getId());
		return ResponseEntity.
				status(HttpStatus.OK).body(resp);
	}
}
