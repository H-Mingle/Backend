package com.hyundai.hmingle.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.hyundai.hmingle.controller.dto.request.ImageCreateRequest;
import com.hyundai.hmingle.controller.dto.response.PostCreateResponse;
import com.hyundai.hmingle.repository.ImageRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageService {

	private final ImageRepository imageRepository;

	public PostCreateResponse saveFiles(Long postId, String content, List<ImageCreateRequest> images) {
		if (CollectionUtils.isEmpty(images)) {
			return null;
		}
		for (ImageCreateRequest image : images) {
			image.setPostId(postId);
		}
		imageRepository.saveAll(images);

		return new PostCreateResponse(postId, content);
	}

	public List<String> getFourImages(Long postId) {
		return imageRepository.getFourImages(postId);
	}

	public void removeImages(Long postId){
		imageRepository.removeImages(postId);
	}

}
