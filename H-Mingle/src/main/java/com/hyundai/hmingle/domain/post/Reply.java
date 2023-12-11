package com.hyundai.hmingle.domain.post;

import org.springframework.stereotype.Component;

import com.hyundai.hmingle.domain.member.Member;
import com.hyundai.hmingle.domain.common.Base;

@Component
public class Reply extends Base {

	private String content;
	private int depth;
	private Reply parent;
	private Member member;
}
