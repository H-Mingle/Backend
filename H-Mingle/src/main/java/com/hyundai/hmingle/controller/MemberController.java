package com.hyundai.hmingle.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hyundai.hmingle.support.JwtTokenExtractor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import com.hyundai.hmingle.controller.dto.response.MemberGetResponse;
import com.hyundai.hmingle.controller.dto.response.MingleResponse;
import com.hyundai.hmingle.service.MemberService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class MemberController {
	private MemberService memberService;
	private JwtTokenExtractor jwtTokenExtractor;

	@GetMapping("/profile")
	public ResponseEntity<MingleResponse<MemberGetResponse>> getPost(@RequestHeader HttpHeaders headers){
		Long memberId = jwtTokenExtractor.extract(headers);
		MemberGetResponse response = memberService.getMember(memberId);

		return ResponseEntity.ok(MingleResponse.success("멤버 조회에 성공하셨습니다.", response));
	}
	
}
