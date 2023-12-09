package com.hyundai.hmingle.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyundai.hmingle.controller.dto.response.MemberGetResponse;
import com.hyundai.hmingle.controller.dto.response.MingleResponse;
import com.hyundai.hmingle.service.MemberService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/member")
@AllArgsConstructor
public class MemberController {
	private MemberService memberService;
	
	@GetMapping("/{memberId}")
	public ResponseEntity<MingleResponse<MemberGetResponse>> getPost(@PathVariable("memberId") Long memberId){
		MemberGetResponse response = memberService.getMember(memberId);
		
		return ResponseEntity.ok(MingleResponse.success("멤버 조회에 성공하셨습니다.", response));
	}
	
}
