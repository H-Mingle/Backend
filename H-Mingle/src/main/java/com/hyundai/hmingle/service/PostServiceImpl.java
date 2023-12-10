package com.hyundai.hmingle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyundai.hmingle.controller.dto.request.PostCreateRequest;
import com.hyundai.hmingle.controller.dto.response.PostGetResponse;

import com.hyundai.hmingle.mapper.ImageMapper;
import com.hyundai.hmingle.mapper.PostMapper;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Log
@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
	
	private PostMapper mapper;
	
	public Long savePost(PostCreateRequest params) {
		return mapper.save(params);
	}

	public PostGetResponse getPost(Long postId) {
		return mapper.getPost(postId);
	}


}
