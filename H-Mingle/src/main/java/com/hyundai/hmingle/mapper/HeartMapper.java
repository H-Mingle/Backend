package com.hyundai.hmingle.mapper;

import com.hyundai.hmingle.mapper.dto.request.HeartMapperRequest;

public interface HeartMapper {

	Long addHeart(HeartMapperRequest params);

	Long removeHeart(HeartMapperRequest params);

	Long findHeart(HeartMapperRequest params);
}
