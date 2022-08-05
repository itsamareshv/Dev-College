package com.amaresh.devcollege.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.amaresh.devcollege.payloads.ApiResponse;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgsNotValidExp(MethodArgumentNotValidException ex)

	{
		Map<String, String> response = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			response.put(fieldName, message);
		});
		return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> handleApiException(ApiException ex) {
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Map<String, String>> resourceNFoundExceptionHandler(HttpMessageNotReadableException ex) {
		Throwable cause = ex.getCause();
		Map<String, String> response = new HashMap<>();
		if (cause instanceof MismatchedInputException) {
			MismatchedInputException missMatchedException = (MismatchedInputException) cause;
			response.put("Feild Aceepts Only Integers =", missMatchedException.getPath().get(0).getFieldName());
			System.out.println("1->>>" + missMatchedException.getPath().get(0).getFieldName());
		}
		return new ResponseEntity<Map<String, String>>(response, null, HttpStatus.BAD_REQUEST);
	}
}
