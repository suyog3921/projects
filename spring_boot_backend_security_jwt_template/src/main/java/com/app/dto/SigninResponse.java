package com.app.dto;

import com.app.entities.UserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class SigninResponse {
	private String jwt;
	private Long id;
	private String firstName;
	private String lastName;
	private String email;	
	private UserRole role;
	private String message;
//	public SigninResponse(String jwt, String message) {
//		super();
//		this.jwt = jwt;
//		this.message = message;
//	}
	public SigninResponse(String jwt, Long id, String firstName, String lastName, String email, UserRole role,
			String message) {
		super();
		this.jwt = jwt;
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
		this.message = message;
	}
	
	
}
