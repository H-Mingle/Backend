package com.hyundai.hmingle.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hyundai.hmingle.controller.dto.request.PostCreateRequest;
import com.hyundai.hmingle.controller.dto.request.PostUpdateRequest;
import com.hyundai.hmingle.controller.dto.response.MingleResponse;
import com.hyundai.hmingle.controller.dto.response.PostCreateResponse;
import com.hyundai.hmingle.controller.dto.response.PostGetResponse;
import com.hyundai.hmingle.controller.dto.response.PostsGetResponse;
import com.hyundai.hmingle.service.PostService;
import com.hyundai.hmingle.support.DateTimeConvertor;
import com.hyundai.hmingle.support.JwtTokenExtractor;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;
	private final JwtTokenExtractor jwtTokenExtractor;
	private final DateTimeConvertor dateTimeConvertor;

	@PostMapping
	public ResponseEntity<MingleResponse<PostCreateResponse>> savePost(
		@RequestPart(required = false) List<MultipartFile> uploadImgs,
		PostCreateRequest params,
		@RequestHeader HttpHeaders headers) {
		Long memberId = jwtTokenExtractor.extract(headers);
		PostCreateResponse response = postService.savePost(params, memberId, uploadImgs);
		return ResponseEntity.ok(MingleResponse.success(
			"게시글 생성에 성공하였습니다.",
			response
		));
	}

	@GetMapping("/images/{postId}")
	public ResponseEntity<MingleResponse<List<byte[]>>> getFourImages(@PathVariable("postId") Long postId) {
		List<byte[]> response = postService.getFourImages(postId);
		return ResponseEntity.ok(MingleResponse.success("이미지 조회에 성공하셨습니다.", response));
	}

	@GetMapping("/{postId}")
	public ResponseEntity<MingleResponse<PostGetResponse>> getPost(@PathVariable("postId") Long postId, @RequestHeader HttpHeaders headers) {
		Long memberId = jwtTokenExtractor.extract(headers);
		PostGetResponse response = postService.getPost(postId, memberId);
		return ResponseEntity.ok(MingleResponse.success("게시글 조회에 성공하셨습니다.", response));
	}

	@DeleteMapping
	public ResponseEntity<MingleResponse<Long>> removePost(@RequestParam("postId") Long postId, @RequestHeader HttpHeaders headers) {
		Long memberId = jwtTokenExtractor.extract(headers);
		Long response = postService.removePost(postId, memberId);
		return ResponseEntity.ok(MingleResponse.success("게시글 삭제에 성공하셨습니다.", response));
	}

	@PatchMapping("/{postId}")
	public ResponseEntity<MingleResponse<PostCreateResponse>> updatePost(
		@PathVariable("postId") Long postId,
		@RequestPart List<MultipartFile> uploadImgs,
		PostUpdateRequest params) {
		PostUpdateRequest request = new PostUpdateRequest(params.getPostId(), params.getContent(), dateTimeConvertor.current());
		PostCreateResponse response = postService.updatePost(request, postId, uploadImgs);
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
