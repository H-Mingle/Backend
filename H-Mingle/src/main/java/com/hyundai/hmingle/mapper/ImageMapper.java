package com.hyundai.hmingle.mapper;

import java.util.List;

import com.hyundai.hmingle.controller.dto.request.ImageCreateRequest;

public interface ImageMapper {
	
	public void saveAll(List<ImageCreateRequest> images);

}
