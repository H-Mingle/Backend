package com.hyundai.hmingle.service;

import com.hyundai.hmingle.controller.dto.request.PostCreateRequest;

public interface PostService {
	
	public Long savePost(PostCreateRequest params);
	
}
