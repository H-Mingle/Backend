package com.hyundai.hmingle.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.hyundai.hmingle.support.JwtTokenExtractor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.multipart.MultipartFile;

import com.hyundai.hmingle.controller.dto.request.ImageCreateRequest;
import com.hyundai.hmingle.controller.dto.request.PostCreateRequest;
import com.hyundai.hmingle.controller.dto.response.MingleResponse;
import com.hyundai.hmingle.controller.dto.response.PostCreateResponse;
import com.hyundai.hmingle.controller.dto.response.PostGetResponse;
import com.hyundai.hmingle.domain.post.ImageUtils;
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
	private JwtTokenExtractor jwtTokenExtractor;


	@PostMapping
	public ResponseEntity<MingleResponse> savePost(@RequestPart(required = false) List<MultipartFile> uploadImgs,
												   PostCreateRequest params,
												   @RequestHeader HttpHeaders headers) {
		Long memberId = jwtTokenExtractor.extract(headers);
		Long postId = postService.savePost(params, memberId);
		List<ImageCreateRequest> images = imageUtils.uploadFiles(uploadImgs);
		PostCreateResponse response = imageService.saveFiles(postId, params.getContent(), images);

		return ResponseEntity.ok(MingleResponse.success("게시글 생성에 성공하였습니다.", response));
	}

	@GetMapping("/images/{postId}")
	public ResponseEntity<MingleResponse<List<byte[]>>> getFourImages(@PathVariable("postId") Long postId){
		List<String> images = imageService.getFourImages(postId);
		List<byte[]> imageByteArrays = new ArrayList<>();

		for (String image : images) {
		    try (InputStream imageStream = new FileInputStream(image)) {
		        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
		        imageByteArrays.add(imageByteArray);
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}

		return ResponseEntity.ok(MingleResponse.success("이미지 조회에 성공하셨습니다.", imageByteArrays));
	}

	@GetMapping("/{postId}")
	public ResponseEntity<MingleResponse<PostGetResponse>> getPost(@PathVariable("postId") Long postId){
		PostGetResponse response = postService.getPost(postId);

		return ResponseEntity.ok(MingleResponse.success("게시글 조회에 성공하셨습니다.", response));
	}

	@DeleteMapping
	public ResponseEntity<MingleResponse<Long>> removePost(@RequestParam("postId") Long postId,  @RequestHeader HttpHeaders headers){
		Long memberId = jwtTokenExtractor.extract(headers);
		return ResponseEntity.ok(MingleResponse.success("게시글 삭제에 성공하셨습니다.", postService.removePost(postId, memberId)));
	}

}
