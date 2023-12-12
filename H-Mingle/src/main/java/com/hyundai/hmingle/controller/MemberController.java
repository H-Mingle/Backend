package com.hyundai.hmingle.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import com.hyundai.hmingle.controller.dto.response.MemberGetResponse;
import com.hyundai.hmingle.controller.dto.response.MingleResponse;
import com.hyundai.hmingle.service.MemberService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/{memberId}")
	public ResponseEntity<MingleResponse<MemberGetResponse>> findById(@PathVariable Long memberId) {
		MemberGetResponse response = memberService.findById(memberId);
		return ResponseEntity.ok(MingleResponse.success(
			"멤버 조회에 성공하셨습니다.",
			response
		));
	}
}
