package com.hyundai.hmingle.service;
import org.springframework.stereotype.Service;


import com.hyundai.hmingle.controller.dto.request.HeartRequest;
import com.hyundai.hmingle.mapper.HeartMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HeartServiceImpl implements HeartService {

	private HeartMapper mapper;
	
	public Long addHeart(HeartRequest params) {
		return mapper.addHeart(params);
	
	}


	public Long removeHeart(HeartRequest params) {
		return mapper.removeHeart(params);
	}

}