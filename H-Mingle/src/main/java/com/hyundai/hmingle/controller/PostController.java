package com.hyundai.hmingle.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hyundai.hmingle.controller.dto.request.ImageCreateRequest;
import com.hyundai.hmingle.controller.dto.request.PostCreateRequest;
import com.hyundai.hmingle.controller.dto.response.MingleResponse;
import com.hyundai.hmingle.controller.dto.response.PostCreateResponse;
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
	public ResponseEntity<MingleResponse> savePost(@RequestPart(required = false) List<MultipartFile> uploadImgs,
						PostCreateRequest params) {
		
		Long postId = postService.savePost(params);
	
		List<ImageCreateRequest> images = imageUtils.uploadFiles(uploadImgs);
		
		PostCreateResponse response = imageService.saveFiles(postId, params.getTitle(), params.getContent(), images);
		return ResponseEntity.ok(MingleResponse.success("�Խñ� ������ �����Ͽ����ϴ�.", response));
	}
}
