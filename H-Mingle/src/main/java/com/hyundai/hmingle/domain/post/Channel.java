package com.hyundai.hmingle.domain.post;

import org.springframework.stereotype.Component;

import com.hyundai.hmingle.domain.common.Base;

@Component
public class Channel extends Base {

	private String name;
	private String location;
	private String description;
	private String businessHours;
	private String phoneNumber;
}
