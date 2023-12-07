package com.hyundai.hmingle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyundai.hmingle.controller.dto.request.ImageCreateRequest;
import com.hyundai.hmingle.mapper.ChannelMapper;
import com.hyundai.hmingle.mapper.ImageMapper;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Log
@Service
@AllArgsConstructor
public class ImageServiceImpl {
	@Autowired
	private ImageMapper mapper;
	
	public void saveFiles(Long postId, List<ImageCreateRequest> images) {
		if(CollectionUtils.isEmpty(images)) {
			return;
		}
		for(ImageCreateRequest image:images) {
			image.setPostId(postId);
		}
		mapper.saveAll(images);
	}

	
}
