package com.hyundai.hmingle.repository;

import org.springframework.stereotype.Repository;

import com.hyundai.hmingle.controller.dto.request.HeartRequest;
import com.hyundai.hmingle.mapper.HeartMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HeartRepository {

	private final HeartMapper heartMapper;

	public Long addHeart(HeartRequest params) {
		return heartMapper.addHeart(params);
	}

	public Long removeHeart(HeartRequest params) {
		return heartMapper.removeHeart(params);
	}
}
