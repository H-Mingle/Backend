package com.hyundai.hmingle.support;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiConnector {

	private static final RestTemplate restTemplate = new RestTemplate();

	static {
		restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
	}

	public static <T> ResponseEntity<T> get(String url, HttpEntity<?> requestEntity, Class<T> responseType) {
		return restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
	}

	public static <T> ResponseEntity<T> post(String url, Object request, Class<T> responseType) {
		return restTemplate.postForEntity(url, request, responseType);
	}
}
