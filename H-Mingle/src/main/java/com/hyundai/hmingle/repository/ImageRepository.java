package com.hyundai.hmingle.repository;

import java.util.List;

import com.hyundai.hmingle.mapper.dto.request.ImagesRequest;
import com.hyundai.hmingle.mapper.dto.response.PostResponse;
import org.springframework.stereotype.Repository;

import com.hyundai.hmingle.controller.dto.request.ImageCreateRequest;
import com.hyundai.hmingle.mapper.ImageMapper;
import com.hyundai.hmingle.mapper.dto.request.MyPostRequest;
import com.hyundai.hmingle.mapper.dto.response.MyPostResponse;

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

	public List<MyPostResponse> findImageUrlsByMemberId(Long memberId, int startRow, int size) {
		MyPostRequest request = new MyPostRequest(memberId, startRow, size);
		return imageMapper.findImageUrlByMemberId(request);
	}

	public void removeImages(Long postId){
		imageMapper.removeImages(postId);
	};

	public List<PostResponse> findByPostId(ImagesRequest request){
		return imageMapper.findByPostId(request);
	}
}
