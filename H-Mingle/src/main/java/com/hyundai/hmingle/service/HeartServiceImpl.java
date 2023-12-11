package com.hyundai.hmingle.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundai.hmingle.controller.dto.request.HeartRequest;
import com.hyundai.hmingle.mapper.HeartMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HeartServiceImpl implements HeartService {

	private final HeartMapper mapper;
	
	public Long addHeart(HeartRequest params) {
		return mapper.addHeart(params);
	}

	public Long removeHeart(HeartRequest params) {
		return mapper.removeHeart(params);
	}
}
