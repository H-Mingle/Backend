package com.hyundai.hmingle.service;

import java.util.List;

import com.hyundai.hmingle.controller.dto.request.ImageCreateRequest;
import com.hyundai.hmingle.controller.dto.response.PostCreateResponse;

public interface ImageService {
	
	public PostCreateResponse saveFiles(Long postId, String title, String content, List<ImageCreateRequest> images);
	
	public byte[] getImageBytes(Long postId);
}
