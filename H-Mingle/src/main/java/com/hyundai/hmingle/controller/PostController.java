package com.hyundai.hmingle.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyundai.hmingle.controller.dto.request.ImageCreateRequest;
import com.hyundai.hmingle.controller.dto.request.PostCreateRequest;
import com.hyundai.hmingle.domain.post.ImageUtils;
import com.hyundai.hmingle.service.ChannelService;
import com.hyundai.hmingle.service.ImageService;
import com.hyundai.hmingle.service.PostService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
	private PostService postService;
	private ImageService imageService;
	private ImageUtils imageUtils;
	
	@PostMapping
	public void savePost(PostCreateRequest params) {
		Long postId = postService.savePost(params);
		List<ImageCreateRequest> images = imageUtils.uploadFiles(params.getFiles());
		imageService.saveFiles(postId, images);
	}
}
