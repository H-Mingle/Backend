package com.hyundai.hmingle.service;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyundai.hmingle.controller.dto.request.ImageCreateRequest;
import com.hyundai.hmingle.controller.dto.response.PostCreateResponse;
import com.hyundai.hmingle.mapper.ImageMapper;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Log
@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService{
	
	private ImageMapper mapper;
	
	public PostCreateResponse saveFiles(Long postId, String title, String content, List<ImageCreateRequest> images) {
		if(CollectionUtils.isEmpty(images)) {
			return null;
		}
		for(ImageCreateRequest image:images) {
			image.setPostId(postId);
		}
		mapper.saveAll(images);
		
		return new PostCreateResponse(postId, title, content);
	}


	public List<String> getFourImages(Long postId){
		return mapper.getFourImages(postId);
	}


}
