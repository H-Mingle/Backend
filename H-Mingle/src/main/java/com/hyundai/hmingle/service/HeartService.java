package com.hyundai.hmingle.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundai.hmingle.controller.dto.request.HeartRequest;
import com.hyundai.hmingle.repository.HeartRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HeartService {

	private final HeartRepository heartRepository;

	public Long addHeart(HeartRequest params) {
		return heartRepository.addHeart(params);
	}

	public Long removeHeart(HeartRequest params) {
		return heartRepository.removeHeart(params);
	}
}
