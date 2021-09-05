package com.aummn.suburb.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.aummn.suburb.resource.dto.response.BaseWebResponse;

/**
 * The global handler class for various exceptions
 *
 * @author James Jin
 * 
 */
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(SuburbNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleSuburbNotFoundException(SuburbNotFoundException snfe) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().timestamp(LocalDateTime.now()).status(HttpStatus.NOT_FOUND.value()).message(snfe.getMessage()).build());
	}

	@ExceptionHandler(SuburbExistsException.class)
	public ResponseEntity<ErrorResponse> handleSuburbExistsException(SuburbExistsException see) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.builder().timestamp(LocalDateTime.now()).status(HttpStatus.CONFLICT.value()).message(see.getMessage()).build());
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ade) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse.builder().timestamp(LocalDateTime.now()).status(HttpStatus.FORBIDDEN.value()).message(ade.getMessage()).build());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> constraintViolationException(ConstraintViolationException cve) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().timestamp(LocalDateTime.now()).status(HttpStatus.BAD_REQUEST.value()).message(cve.getMessage()).build());
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
	  MethodArgumentNotValidException manve, 
	  HttpHeaders headers, 
	  HttpStatus status, 
	  WebRequest request) {
		// Get all fields errors
		List<String> errors = manve.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());
	    
		ErrorResponse errorResponse = ErrorResponse.builder().timestamp(LocalDateTime.now()).status(HttpStatus.BAD_REQUEST.value()).message(manve.getLocalizedMessage()).errors(errors).build();
		return new ResponseEntity<>(errorResponse, headers, status);
	}

}
