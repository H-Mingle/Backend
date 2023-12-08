package com.hyundai.hmingle.mapper;

import com.hyundai.hmingle.controller.dto.request.PostCreateRequest;

public interface PostMapper {

	public Long save(PostCreateRequest params);
	
}
