package com.hyundai.hmingle.mapper;

import com.hyundai.hmingle.controller.dto.request.HeartRequest;

public interface HeartMapper {

	Long addHeart(HeartRequest params);

	Long removeHeart(HeartRequest params);

	Long findHeart(HeartRequest params);
}
