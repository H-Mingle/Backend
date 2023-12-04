package com.hyundai.hmingle.domain.post;

import org.springframework.stereotype.Component;

import com.hyundai.hmingle.domain.common.Base;

@Component
public class Image extends Base {

	private String imageUrl;
	private int sequence;
	private Integer postId;
}
