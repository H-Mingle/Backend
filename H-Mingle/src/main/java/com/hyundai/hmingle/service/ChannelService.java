package com.hyundai.hmingle.service;

import java.util.List;

import com.hyundai.hmingle.controller.dto.response.ChannelGetResponse;


public interface ChannelService {
	public List<ChannelGetResponse> getList();
}
