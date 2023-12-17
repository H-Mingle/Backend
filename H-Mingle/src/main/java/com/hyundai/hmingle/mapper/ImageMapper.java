package com.hyundai.hmingle.mapper;

import java.util.List;

import com.hyundai.hmingle.controller.dto.request.ImageCreateRequest;
import com.hyundai.hmingle.mapper.dto.request.ImagesMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.MyPostMapperRequest;
import com.hyundai.hmingle.mapper.dto.response.MyPostMapperResponse;
import com.hyundai.hmingle.mapper.dto.response.PostMapperResponse;

public interface ImageMapper {

	List<String> getFourImages(Long postId);

	List<String> getImages(Long postId);

	List<MyPostMapperResponse> findImageUrlByMemberId(MyPostMapperRequest request);

	List<MyPostMapperResponse> findImageUrlLikedByMemberId(MyPostMapperRequest request);

	void saveAll(List<ImageCreateRequest> images);

	void removeImages(Long postId);

	public List<PostMapperResponse> findByPostId(ImagesMapperRequest request);

}
