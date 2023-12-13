package com.hyundai.hmingle.mapper;

import java.util.List;

import com.hyundai.hmingle.controller.dto.request.ImageCreateRequest;
import com.hyundai.hmingle.mapper.dto.request.ImagesRequest;
import com.hyundai.hmingle.mapper.dto.response.PostResponse;
import com.hyundai.hmingle.mapper.dto.request.MyPostRequest;
import com.hyundai.hmingle.mapper.dto.response.MyPostResponse;

public interface ImageMapper {
	
	List<String> getFourImages(Long postId);

	List<String> getImages(Long postId);

	List<MyPostResponse> findImageUrlByMemberId(MyPostRequest request);

	List<MyPostResponse> findImageUrlLikedByMemberId(MyPostRequest request);

	void saveAll(List<ImageCreateRequest> images);

	void removeImages(Long postId);

	public List<PostResponse> findByPostId(ImagesRequest request);

}
