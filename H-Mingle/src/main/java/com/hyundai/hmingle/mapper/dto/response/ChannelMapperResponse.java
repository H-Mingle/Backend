package com.hyundai.hmingle.mapper.dto.response;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ChannelMapperResponse {

	private final Long channelId;
	private final String name;
	private final String location;
	private final String description;
	private final String businessHours;
	private final String phoneNumber;
	private final Integer count;
	private final LocalDateTime modifiedDate;
}
