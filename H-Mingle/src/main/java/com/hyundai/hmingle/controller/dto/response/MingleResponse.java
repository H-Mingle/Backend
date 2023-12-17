package com.hyundai.hmingle.controller.dto.response;

import lombok.Getter;

@Getter
public class MingleResponse<T> {

	private boolean success;
	private String message;
	private T data;

	private MingleResponse(boolean success, String message, T data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}

	public static <T> MingleResponse<T> success(String message, T data) {
		return new MingleResponse<T>(true, message, data);
	}

	public static <T> MingleResponse<T> fail(String message) {
		return new MingleResponse<T>(false, message, null);
	}
}
