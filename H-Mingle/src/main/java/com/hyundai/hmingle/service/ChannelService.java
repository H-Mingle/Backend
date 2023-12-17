package com.hyundai.hmingle.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundai.hmingle.controller.dto.response.ChannelGetResponse;
import com.hyundai.hmingle.mapper.dto.response.ChannelMapperResponse;
import com.hyundai.hmingle.repository.ChannelRepository;
import com.hyundai.hmingle.support.DateTimeConvertor;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ChannelService {

	private final ChannelRepository channelRepository;
	private final DateTimeConvertor dateTimeConvertor;

	@Transactional(readOnly = true)
	public List<ChannelGetResponse> getList() {
		List<ChannelMapperResponse> channels = channelRepository.getList();
		return channels.stream()
			.map(channel -> new ChannelGetResponse(
				channel.getChannelId(),
				channel.getName(),
				channel.getLocation(),
				channel.getDescription(),
				channel.getBusinessHours(),
				channel.getPhoneNumber(),
				channel.getCount(),
				dateTimeConvertor.calculate(channel.getModifiedDate(), "작성 게시물이 없습니다.")
			))
			.collect(Collectors.toUnmodifiableList());
	}
}
