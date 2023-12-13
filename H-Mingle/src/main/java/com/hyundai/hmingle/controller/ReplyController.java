package com.hyundai.hmingle.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyundai.hmingle.controller.dto.request.ReplyCreateRequest;
import com.hyundai.hmingle.controller.dto.request.ReplyUpdateRequest;
import com.hyundai.hmingle.controller.dto.response.MingleResponse;
import com.hyundai.hmingle.controller.dto.response.ReplyCreateResponse;
import com.hyundai.hmingle.controller.dto.response.ReplyDetailResponse;
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

	@PatchMapping("/{replyId}")
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

	@DeleteMapping("/{replyId}")
	public ResponseEntity<MingleResponse<Void>> delete(
		@RequestHeader HttpHeaders headers,
		@PathVariable Long postId,
		@PathVariable Long replyId) {
		Long memberId = jwtTokenExtractor.extract(headers);
		replyService.delete(memberId, postId, replyId);
		return ResponseEntity.ok(MingleResponse.success(
			"댓글 삭제에 성공하였습니다.",
			null
		));
	}

	@GetMapping
	public ResponseEntity<MingleResponse<List<ReplyDetailResponse>>> findAllWithPaging(
		@PathVariable Long postId,
		@RequestParam Integer page,
		@RequestParam Integer size,
		@RequestParam(required = false) Long parentId) {
		List<ReplyDetailResponse> responses = replyService.findAllWithPaging(postId, page, size, parentId);
		return ResponseEntity.ok(MingleResponse.success(
			"댓글 목록 조회에 성공하였습니다.",
			responses
		));
	}
}
