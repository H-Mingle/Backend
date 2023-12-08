package com.hyundai.hmingle.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyundai.hmingle.controller.dto.request.ImageCreateRequest;
import com.hyundai.hmingle.controller.dto.response.PostCreateResponse;
import com.hyundai.hmingle.mapper.ChannelMapper;
import com.hyundai.hmingle.mapper.ImageMapper;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Log
@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService{
	@Autowired
	private ImageMapper mapper;
	
	public PostCreateResponse saveFiles(Long postId, String title, String content, List<ImageCreateRequest> images) {
		if(CollectionUtils.isEmpty(images)) {
			return null;
		}
		for(ImageCreateRequest image:images) {
			image.setPostId(postId);
		}
		mapper.saveAll(images);
		
		return new PostCreateResponse(postId, title, content);
	}


	public byte[] getImageBytes(Long postId) {
		byte[] imageBytes = null;
		
		List<String> images = mapper.getImages(postId);
		
		for(String image:images) {
			try {
				imageBytes = IOUtils.toByteArray(new FileInputStream(image));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return imageBytes;
	}

	
}
