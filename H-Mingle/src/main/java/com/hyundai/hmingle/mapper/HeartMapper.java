package com.hyundai.hmingle.mapper;

import com.hyundai.hmingle.controller.dto.request.HeartRequest;

public interface HeartMapper {
	public Long addHeart(HeartRequest params);
	public Long removeHeart(HeartRequest params);
	public Long findHeart(HeartRequest params);
}