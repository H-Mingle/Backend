package com.hyundai.hmingle.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyundai.hmingle.controller.dto.response.MingleResponse;
import com.hyundai.hmingle.controller.dto.response.MyPostsResponse;
import com.hyundai.hmingle.service.MyPageService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/members/{memberId}/posts")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MyPageController {

	private final MyPageService myPageService;

	@GetMapping
	public ResponseEntity<MingleResponse<MyPostsResponse>> findPostsWrittenMember(
		@PathVariable Long memberId,
		@RequestParam Integer page,
		@RequestParam Integer size) {
		MyPostsResponse response = myPageService.findPostsWrittenMember(memberId, page, size);
		return ResponseEntity.ok(MingleResponse.success(
			"사용자가 작성한 게시글 목록 조회에 성공하였습니다.",
			response
		));
	}

	@GetMapping("/like")
	public ResponseEntity<MingleResponse<MyPostsResponse>> findPostsLikedMember(
		@PathVariable Long memberId,
		@RequestParam Integer page,
		@RequestParam Integer size) {
		MyPostsResponse response = myPageService.findPostsLikedMember(memberId, page, size);
		return ResponseEntity.ok(MingleResponse.success(
			"사용자가 좋아요한 게시글 목록 조회에 성공하였습니다.",
			response
		));
	}
}
