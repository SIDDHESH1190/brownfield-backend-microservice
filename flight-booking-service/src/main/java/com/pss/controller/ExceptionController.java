package com.pss.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
@CrossOrigin(origins = "*")
public class ExceptionController {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumnetNotValid(MethodArgumentNotValidException e) {

		List<String> errors = e.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList();
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException e) {
		List<String> errors = e.getConstraintViolations().stream().map(error -> error.getMessage()).toList();
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Object> handleException(RuntimeException e) {
		return new ResponseEntity<Object>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
}
