package com.hyundai.hmingle.domain.post;

import org.springframework.stereotype.Component;

import com.hyundai.hmingle.domain.member.Member;
import com.hyundai.hmingle.domain.common.Base;

@Component
public class Post extends Base {
	private String title;
	private String content;
	private Integer readCount;
	private Member member;
	private Channel channel;
}
