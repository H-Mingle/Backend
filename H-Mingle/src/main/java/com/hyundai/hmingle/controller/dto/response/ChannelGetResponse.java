package com.hyundai.hmingle.controller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ChannelGetResponse {

	private final Long channelId;
	private final String name;
	private final String location;
	private final String description;
	private final String businessHours;
	private final String phoneNumber;
	private final Integer count;
	private String recent;
}
