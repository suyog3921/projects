package com.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ApiResponse;
import com.app.dto.SigninRequest;
import com.app.dto.Signup;
import com.app.entities.UserEntity;
import com.app.service.UserService;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> signup(@RequestBody Signup signup) {
        ApiResponse response = userService.userRegistration(signup);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value = "/getUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserEntity>> getUsers() {
        List<UserEntity> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }
    
    
    
    @PostMapping("/signin") 
	public ResponseEntity<?> signInUser(
			@RequestBody @Valid SigninRequest request) {
		//@RequestBody => Json -> Java (un marshalling | de ser)
		System.out.println("in signin " + request);
		System.out.println("service "+userService);
			return ResponseEntity.ok(
					userService.authenticateUser(request));
		
	}
}
