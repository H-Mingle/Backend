package com.hyundai.hmingle.controller.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageCreateRequest {

	private Long id;
	private Long postId;
	private int sequence;
	private String originalName;
	private String saveName;
	private long size;

	public ImageCreateRequest(String originalName, String saveName, long size) {
		this.originalName = originalName;
		this.saveName = saveName;
		this.size = size;
	}
}
