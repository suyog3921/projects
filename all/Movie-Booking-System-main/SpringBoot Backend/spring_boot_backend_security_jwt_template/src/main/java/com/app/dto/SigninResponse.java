package com.app.dto;

import com.app.entities.UserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SigninResponse {
//	private String jwt;
	private Long id;
	private String firstName;
	private String lastName;
	private String email;	
	private UserRole role;
	private String mesg;
}
