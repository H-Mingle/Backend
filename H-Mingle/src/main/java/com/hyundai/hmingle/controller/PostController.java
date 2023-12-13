package com.hyundai.hmingle.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.hyundai.hmingle.controller.dto.request.PostUpdateRequest;
import com.hyundai.hmingle.controller.dto.response.*;
import com.hyundai.hmingle.support.ImageConvertor;
import com.hyundai.hmingle.support.JwtTokenExtractor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import com.hyundai.hmingle.controller.dto.request.ImageCreateRequest;
import com.hyundai.hmingle.controller.dto.request.PostCreateRequest;
import com.hyundai.hmingle.domain.post.ImageUtils;
import com.hyundai.hmingle.service.ImageService;

import com.hyundai.hmingle.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;
	private final ImageService imageService;
	private final ImageUtils imageUtils;
	private final ImageConvertor imageConvertor;
	private final JwtTokenExtractor jwtTokenExtractor;

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
		List<byte[]> imageByteArrays = images.stream()
			.map(imageConvertor::convertPath)
			.collect(Collectors.toUnmodifiableList());

		return ResponseEntity.ok(MingleResponse.success("이미지 조회에 성공하셨습니다.", imageByteArrays));
	}

	@GetMapping("/{postId}")
	public ResponseEntity<MingleResponse<PostGetResponse>> getPost(@PathVariable("postId") Long postId,
																   @RequestHeader(name="memberId", required = false) Long memberId){
		PostGetResponse response = postService.getPost(postId, memberId);
		return ResponseEntity.ok(MingleResponse.success("게시글 조회에 성공하셨습니다.", response));
	}

	@DeleteMapping
	public ResponseEntity<MingleResponse<Long>> removePost(@RequestParam("postId") Long postId,  @RequestHeader HttpHeaders headers){
		Long memberId = jwtTokenExtractor.extract(headers);
		return ResponseEntity.ok(MingleResponse.success("게시글 삭제에 성공하셨습니다.", postService.removePost(postId, memberId)));
	}

	@PatchMapping("/{postId}")
	public ResponseEntity<MingleResponse<PostCreateResponse>> updatePost(@PathVariable("postId") Long postId,
																		 @RequestPart List<MultipartFile> uploadImgs,
																		 PostUpdateRequest params){

		PostUpdateRequest request = new PostUpdateRequest(params.getPostId(), params.getContent());
		postService.updatePost(request);

		List<ImageCreateRequest> images = imageUtils.uploadFiles(uploadImgs);

        imageService.removeImages(postId);
		PostCreateResponse response = imageService.saveFiles(postId, params.getContent(), images);
		return ResponseEntity.ok(MingleResponse.success("게시글 수정에 성공하였습니다.", response));
	}



	@GetMapping
	public ResponseEntity<MingleResponse<PostsGetResponse>> getPostsByChannel(@RequestParam("page") int page,
																			  @RequestParam("size") int size,
																			  @RequestHeader("channel") Long channelId) {
		PostsGetResponse response = postService.getPostsByChannel(channelId, page, size);
		return ResponseEntity.ok(MingleResponse.success("게시글 리스트 조회에 성공하셨습니다.", response));
	}


}