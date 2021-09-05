package com.aummn.suburb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.aummn.suburb.resource.dto.response.BaseWebResponse;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(SuburbNotFoundException.class)
	public ResponseEntity<BaseWebResponse> handleEntityNotFoundException(SuburbNotFoundException snfe) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseWebResponse
				.error(ErrorResponse.builder().status(HttpStatus.NOT_FOUND.value()).message(snfe.getMessage()).build()));
	}
}
