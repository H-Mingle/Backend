package com.hyundai.hmingle.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyundai.hmingle.controller.dto.request.ReplyCreateRequest;
import com.hyundai.hmingle.controller.dto.request.ReplyUpdateRequest;
import com.hyundai.hmingle.controller.dto.response.MingleResponse;
import com.hyundai.hmingle.controller.dto.response.ReplyCreateResponse;
import com.hyundai.hmingle.controller.dto.response.ReplyUpdateResponse;
import com.hyundai.hmingle.service.ReplyService;
import com.hyundai.hmingle.support.JwtTokenExtractor;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts/{postId}/replies")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ReplyController {

	private final ReplyService replyService;
	private final JwtTokenExtractor jwtTokenExtractor;

	@PostMapping
	public ResponseEntity<MingleResponse<ReplyCreateResponse>> save(
		@RequestHeader HttpHeaders headers,
		@PathVariable Long postId,
		@RequestBody ReplyCreateRequest request) {
		Long memberId = jwtTokenExtractor.extract(headers);
		ReplyCreateResponse response = replyService.save(memberId, postId, request);
		return ResponseEntity.ok(MingleResponse.success(
			"댓글 작성에 성공하였습니다.",
			response
		));
	}

	@PostMapping("/{replyId}")
	public ResponseEntity<MingleResponse<ReplyUpdateResponse>> update(
		@RequestHeader HttpHeaders headers,
		@PathVariable Long postId,
		@PathVariable Long replyId,
		@RequestBody ReplyUpdateRequest request) {
		Long memberId = jwtTokenExtractor.extract(headers);
		ReplyUpdateResponse response = replyService.update(memberId, postId, replyId, request);
		return ResponseEntity.ok(MingleResponse.success(
			"댓글 수정에 성공하였습니다.",
			response
		));
	}
}
