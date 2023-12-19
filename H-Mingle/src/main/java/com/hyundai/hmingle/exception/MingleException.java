package com.hyundai.hmingle.exception;

public class MingleException extends RuntimeException {

	public MingleException(String message) {
		super(message);
	}

	public MingleException(Exception exception) {
		super(exception.getMessage());
	}
}
