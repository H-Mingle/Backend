package com.hyundai.hmingle.service;

import java.util.HashMap;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.hyundai.hmingle.controller.dto.request.HeartRequest;

public interface HeartService {
	public Long addHeart(HeartRequest params);
	public Long removeHeart(HeartRequest params);
}

