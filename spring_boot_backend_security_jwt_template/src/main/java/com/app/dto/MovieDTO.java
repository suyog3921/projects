package com.app.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

	//@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	private String mName;
	private String mDescription;
	private int mRating;
	private String movieImageName;
	
//	public MovieDTO(@NotBlank String mName, @NotBlank String mDesc, int rating) {
//		super();
//		this.mName = mName;
//		this.mDesc = mDesc;
//		this.rating = rating;
//	}
	
	   
}
