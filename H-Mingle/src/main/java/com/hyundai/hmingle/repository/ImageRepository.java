package com.hyundai.hmingle.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyundai.hmingle.controller.dto.request.ImageCreateRequest;
import com.hyundai.hmingle.mapper.ImageMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageRepository {

	private final ImageMapper imageMapper;

	public void saveAll(List<ImageCreateRequest> images) {
		imageMapper.saveAll(images);
	}

	public List<String> getFourImages(Long postId) {
		return imageMapper.getFourImages(postId);
	}

	public void removeImages(Long postId){
		imageMapper.removeImages(postId);
	};
}
