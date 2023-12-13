package com.hyundai.hmingle.controller;

import com.hyundai.hmingle.controller.dto.request.MemberUpdateRequest;
import com.hyundai.hmingle.mapper.dto.response.MemberUpdateResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import com.hyundai.hmingle.controller.dto.response.MemberGetResponse;
import com.hyundai.hmingle.controller.dto.response.MingleResponse;
import com.hyundai.hmingle.service.MemberService;
import com.hyundai.hmingle.support.JwtTokenExtractor;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberController {

	private final MemberService memberService;
	private final JwtTokenExtractor jwtTokenExtractor;

	@GetMapping("/{memberId}")
	public ResponseEntity<MingleResponse<MemberGetResponse>> findById(@PathVariable Long memberId) {
		MemberGetResponse response = memberService.findById(memberId);
		return ResponseEntity.ok(MingleResponse.success(
			"사용자 상세 조회에 성공하셨습니다.",
			response
		));
	}

	@PatchMapping("/{memberId}")
	public ResponseEntity<MingleResponse<MemberUpdateResponse>> update(@PathVariable Long memberId,
																	   @RequestBody MemberUpdateRequest params) {

		MemberUpdateResponse response = memberService.update(params);
		return ResponseEntity.ok(MingleResponse.success(
				"사용자 수정에 성공하였습니다.",
				response
		));
	}

	@DeleteMapping("/leave")
	public ResponseEntity<MingleResponse<Void>> leave(@RequestHeader HttpHeaders headers) {
		Long memberId = jwtTokenExtractor.extract(headers);
		memberService.leave(memberId);
		return ResponseEntity.ok(MingleResponse.success(
			"사용자 탈퇴에 성공하였습니다.",
			null
		));
	}
}
