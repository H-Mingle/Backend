package com.hyundai.hmingle.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hyundai.hmingle.controller.dto.response.MingleResponse;

@RestControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<MingleResponse<Object>> serverError(Exception e) {
		return ResponseEntity.status(500).body(MingleResponse.fail(e.getMessage()));
	}
}
