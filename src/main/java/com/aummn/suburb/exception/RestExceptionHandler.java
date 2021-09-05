package com.aummn.suburb.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
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

import lombok.extern.slf4j.Slf4j;

/**
 * The global handler class for various exceptions
 *
 * @author James Jin
 * 
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(SuburbNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleSuburbNotFoundException(SuburbNotFoundException snfe) {
		log.error(ExceptionUtils.getStackTrace(snfe));
		String msg = Optional.ofNullable(snfe.getMessage()).orElse("Suburb not found");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().timestamp(LocalDateTime.now()).status(HttpStatus.NOT_FOUND.value()).message(msg).build());
	}

	@ExceptionHandler(SuburbExistsException.class)
	public ResponseEntity<ErrorResponse> handleSuburbExistsException(SuburbExistsException see) {
		log.error(ExceptionUtils.getStackTrace(see));
		String msg = Optional.ofNullable(see.getMessage()).orElse("Suburb exists");
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.builder().timestamp(LocalDateTime.now()).status(HttpStatus.CONFLICT.value()).message(msg).build());
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ade) {
		log.error(ExceptionUtils.getStackTrace(ade));
		String msg = Optional.ofNullable(ade.getMessage()).orElse("Access denied");
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse.builder().timestamp(LocalDateTime.now()).status(HttpStatus.FORBIDDEN.value()).message(msg).build());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException iae) {
		log.error(ExceptionUtils.getStackTrace(iae));
		String msg = Optional.ofNullable(iae.getMessage()).orElse("Invalid argument");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().timestamp(LocalDateTime.now()).status(HttpStatus.BAD_REQUEST.value()).message(msg).build());
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> constraintViolationException(ConstraintViolationException cve) {
		log.error(ExceptionUtils.getStackTrace(cve));
		String msg = Optional.ofNullable(cve.getMessage()).orElse("Constraint violation");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().timestamp(LocalDateTime.now()).status(HttpStatus.BAD_REQUEST.value()).message(msg).build());
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
	  MethodArgumentNotValidException manve, 
	  HttpHeaders headers, 
	  HttpStatus status, 
	  WebRequest request) {
		
		log.error(ExceptionUtils.getStackTrace(manve));
		
		// Get all fields errors
		List<String> errors = manve.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());
		StringBuilder errorSB = new StringBuilder();
		errors.stream().forEach(error -> errorSB.append(error).append(" "));
	    
		String msg = Optional.ofNullable(errorSB.toString()).orElse("Method argument not valid");
		ErrorResponse errorResponse = ErrorResponse.builder().timestamp(LocalDateTime.now()).status(HttpStatus.BAD_REQUEST.value()).message(msg).build();
		return new ResponseEntity<>(errorResponse, headers, status);
	}

}
