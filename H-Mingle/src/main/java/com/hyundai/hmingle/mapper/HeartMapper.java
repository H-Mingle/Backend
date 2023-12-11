package com.hyundai.hmingle.mapper;

import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestParam;

import com.hyundai.hmingle.controller.dto.request.HeartRequest;

public interface HeartMapper {
	public Long addHeart(HeartRequest params);
	public Long removeHeart(HeartRequest params);
	public Long findHeart(Long postId, Long memberId);
}