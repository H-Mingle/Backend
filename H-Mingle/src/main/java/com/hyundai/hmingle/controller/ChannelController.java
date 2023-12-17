package com.hyundai.hmingle.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyundai.hmingle.controller.dto.response.ChannelGetResponse;
import com.hyundai.hmingle.controller.dto.response.MingleResponse;
import com.hyundai.hmingle.service.ChannelServiceImpl;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/channels")
@AllArgsConstructor
public class ChannelController {

	private ChannelServiceImpl channelService;
	
	@GetMapping
	public ResponseEntity<MingleResponse<List<ChannelGetResponse>>> getList(){
		return ResponseEntity.ok(MingleResponse.success("영업점 리스트 조회 성공",channelService.getList()));
	}
}
