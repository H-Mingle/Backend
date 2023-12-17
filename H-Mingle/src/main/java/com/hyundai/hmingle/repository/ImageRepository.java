package com.hyundai.hmingle.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.hyundai.hmingle.controller.dto.request.ImageCreateRequest;
import com.hyundai.hmingle.mapper.ImageMapper;
import com.hyundai.hmingle.mapper.dto.request.ImageCreateMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.ImagesMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.MyPostMapperRequest;
import com.hyundai.hmingle.mapper.dto.response.MyPostMapperResponse;
import com.hyundai.hmingle.mapper.dto.response.PostMapperResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageRepository {

	private final ImageMapper imageMapper;

	public void saveAll(List<ImageCreateRequest> images) {
		List<ImageCreateMapperRequest> requests = images.stream()
			.map(image -> new ImageCreateMapperRequest(image.getOriginalName(), image.getSaveName(), image.getSize()))
			.collect(Collectors.toUnmodifiableList());
		imageMapper.saveAll(requests);
	}

	public List<String> getFourImages(Long postId) {
		return imageMapper.getFourImages(postId);
	}

	public List<MyPostMapperResponse> findImageUrlsByMemberId(Long memberId, int startRow, int size) {
		MyPostMapperRequest request = new MyPostMapperRequest(memberId, startRow, size);
		return imageMapper.findImageUrlByMemberId(request);
	}

	public List<MyPostMapperResponse> findImageUrlLikedByMemberId(Long memberId, int startRow, int size) {
		MyPostMapperRequest request = new MyPostMapperRequest(memberId, startRow, size);
		return imageMapper.findImageUrlLikedByMemberId(request);
	}

	public void removeImages(Long postId) {
		imageMapper.removeImages(postId);
	}

	public List<PostMapperResponse> findByPostId(ImagesMapperRequest request) {
		return imageMapper.findByPostId(request);
	}
}
