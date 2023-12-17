package com.hyundai.hmingle.repository;

import org.springframework.stereotype.Repository;

import com.hyundai.hmingle.mapper.HeartMapper;
import com.hyundai.hmingle.mapper.dto.request.HeartMapperRequest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HeartRepository {

	private final HeartMapper heartMapper;

	public Long addHeart(Long postId, Long memberId) {
		HeartMapperRequest heartMapperRequest = new HeartMapperRequest(postId, memberId);
		return heartMapper.addHeart(heartMapperRequest);
	}

	public Long removeHeart(Long postId, Long memberId) {
		HeartMapperRequest heartMapperRequest = new HeartMapperRequest(postId, memberId);
		return heartMapper.removeHeart(heartMapperRequest);
	}

	public Long findHeart(Long postId, Long memberId) {
		HeartMapperRequest heartMapperRequest = new HeartMapperRequest(postId, memberId);
		return heartMapper.findHeart(heartMapperRequest);
	}
}
