package com.hyundai.hmingle.controller.dto.response;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChannelGetResponse {
	private Long channelId;
	private String name;
	private String location;
	private String description;
	private String businessHours;
	private String phoneNumber;
	private Integer count;
	private String recent;
	
}
