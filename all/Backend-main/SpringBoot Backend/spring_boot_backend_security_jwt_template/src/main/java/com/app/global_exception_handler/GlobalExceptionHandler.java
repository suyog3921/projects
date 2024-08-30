package com.app.global_exception_handler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.app.custom_exceptions.AuthenticationException;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.ApiResponse;

@RestControllerAdvice // =@ControllerAdvice +
//@ResponseBody added on ret types of exc handling methods
public class GlobalExceptionHandler {
	// add exc handling methods
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
		System.out.println("in res not found exc ");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage()));
	}

	// add catch-all exc handling method
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handleAnyException(RuntimeException e) {
		System.out.println("in catch all " + e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage()));
	}
	// add @RequestBody validation exc handling method
		@ExceptionHandler(MethodArgumentNotValidException.class)
		@ResponseStatus(HttpStatus.BAD_REQUEST)
		public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
			System.out.println("in catch all " + e);
			 List<FieldError> list = e.getFieldErrors();
			 /*
			  * Convert List<FieldError> -> Map<K,V>
			  * K -FieldError: getField()
			  * V -FieldError: getDefaultMessage()
			  */
			 Map<String, String> map = list.stream() //Stream<FieldError>
			 .collect(Collectors.toMap
					 (FieldError::getField, FieldError::getDefaultMessage));//f -> f.getField() 
			return map;
		}
		

		// User Authentication
		 @ExceptionHandler(AuthenticationException.class)
		    public ResponseEntity<?> handleValidationExceptions(AuthenticationException ex) {
			 System.out.println("in authentication exc ");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(ex.getMessage()));
		    }


		    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
		    public ResponseEntity<?> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
		        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
		                .body("Content type not supported: " + ex.getContentType());
		    }
		    
		    @ExceptionHandler(UsernameNotFoundException.class)
		    public ResponseEntity<?> handleHttpMediaTypeNotSupportedException(UsernameNotFoundException ex) {
		        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
		                .body(new ApiResponse(ex.getMessage()));
		    }
		

}
