package com.hyundai.hmingle.service;

import java.util.List;

import com.hyundai.hmingle.controller.dto.request.ImageCreateRequest;

public interface ImageService {
	
	public void saveFiles(Long postId, List<ImageCreateRequest> images);
	public void saveAll(List<ImageCreateRequest> images);

}
