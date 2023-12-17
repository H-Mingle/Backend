package com.hyundai.hmingle.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyundai.hmingle.controller.dto.request.HeartRequest;
import com.hyundai.hmingle.controller.dto.response.MingleResponse;
import com.hyundai.hmingle.exception.ControllerAdvice;
import com.hyundai.hmingle.service.HeartServiceImpl;
import com.hyundai.hmingle.support.JwtTokenExtractor;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/like")
@AllArgsConstructor
public class HeartController {

	private HeartServiceImpl heartService;
	private JwtTokenExtractor jwtTokenExtractor;
	
	@PostMapping
	public ResponseEntity<MingleResponse<Long>> addHeart(@RequestParam("postId") Long postId, @RequestHeader HttpHeaders headers){
		Long memberId = jwtTokenExtractor.extract(headers);
		HeartRequest params = new HeartRequest(postId, memberId);
		return ResponseEntity.ok(MingleResponse.success("좋아요 추가에 성공하셨습니다.", heartService.addHeart(params)));
	}
	
	@PutMapping
	public ResponseEntity<MingleResponse<Long>> removeHeart(@RequestParam("postId") Long postId,  @RequestHeader HttpHeaders headers){
		Long memberId = jwtTokenExtractor.extract(headers);
		HeartRequest params = new HeartRequest(postId, memberId);
		return ResponseEntity.ok(MingleResponse.success("좋아요 취소에 성공하셨습니다.", heartService.removeHeart(params)));
	}
}
