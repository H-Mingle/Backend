package com.hyundai.hmingle.support;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import lombok.extern.java.Log;

@Log
public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		if (response.getStatusCode().is4xxClientError()) {
			log.info(String.format("[%d %s] Oauth 서버와 연결 실패", response.getRawStatusCode(), response.getStatusText()));
			throw new RuntimeException("Oauth 서버와의 연결에 실패하였습니다. 입력 값을 확인해주세요.");
		}
		if (response.getStatusCode().is5xxServerError()) {
			log.info(String.format("[%d %s] Oauth 서버와 연결 실패", response.getRawStatusCode(), response.getStatusText()));
			throw new RuntimeException("Oauth 서버에 문제가 발생하여 연결에 실패하였습니다. 관리자에게 문의 해주세요.");
		}
	}
}
