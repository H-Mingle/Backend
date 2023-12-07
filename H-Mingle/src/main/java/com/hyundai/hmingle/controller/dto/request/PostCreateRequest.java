package com.hyundai.hmingle.controller.dto.request;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;


import lombok.Data;

@Data
public class PostCreateRequest {
	private Long postId;
	private String title;
	private String content;
	private List<MultipartFile> files = new ArrayList<>(); 
	private Long channelId;
	private Long memberId;
	
	
}
