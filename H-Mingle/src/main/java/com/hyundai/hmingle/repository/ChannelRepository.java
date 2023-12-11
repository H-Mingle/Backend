package com.hyundai.hmingle.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyundai.hmingle.controller.dto.response.ChannelGetResponse;
import com.hyundai.hmingle.mapper.ChannelMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ChannelRepository {

	private final ChannelMapper channelMapper;

	public List<ChannelGetResponse> getList() {
		return channelMapper.getList();
	}
}
