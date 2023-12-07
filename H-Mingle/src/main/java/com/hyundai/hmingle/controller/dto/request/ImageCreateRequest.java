package com.hyundai.hmingle.controller.dto.request;

import lombok.Data;

@Data
public class ImageCreateRequest {
	private Long id;
	private Long postId;
	private int sequence;
	private String originalName;
	private String saveName;
	private long size;
	
	public ImageCreateRequest(int sequence, String originalName, String saveName, long size) {
		this.sequence = sequence;
		this.originalName = originalName;
		this.saveName = saveName;
		this.size = size;
	}

}
