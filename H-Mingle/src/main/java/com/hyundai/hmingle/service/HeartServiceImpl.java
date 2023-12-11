package com.hyundai.hmingle.service;

import java.util.HashMap;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

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