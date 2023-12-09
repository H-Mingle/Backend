package com.hyundai.hmingle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyundai.hmingle.controller.dto.response.MemberGetResponse;
import com.hyundai.hmingle.mapper.ChannelMapper;
import com.hyundai.hmingle.mapper.MemberMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberMapper mapper;

	public MemberGetResponse getMember(Long memberId) {
		return mapper.getMember(memberId);
	}

}
