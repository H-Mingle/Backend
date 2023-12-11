package com.hyundai.hmingle.mapper;

import com.hyundai.hmingle.controller.dto.request.PostCreateRequest;
import com.hyundai.hmingle.controller.dto.response.PostGetResponse;


public interface PostMapper {

	public Long save(PostCreateRequest params);
	
	public PostGetResponse getPost(Long postId);
	
	public Long findMember(Long postId);


}
