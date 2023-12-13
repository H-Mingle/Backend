package com.hyundai.hmingle.mapper;

import java.util.List;

import com.hyundai.hmingle.controller.dto.request.ImageCreateRequest;
import com.hyundai.hmingle.mapper.dto.request.ImagesRequest;
import com.hyundai.hmingle.mapper.dto.response.PostResponse;

public interface ImageMapper {
	
	public void saveAll(List<ImageCreateRequest> images);

	public List<String> getFourImages(Long postId);

	public List<String> getImages(Long postId);

	void removeImages(Long postId);

	public List<PostResponse> findByPostId(ImagesRequest request);

}