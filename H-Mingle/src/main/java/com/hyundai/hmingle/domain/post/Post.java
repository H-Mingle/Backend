package com.hyundai.hmingle.domain.post;

import java.util.ArrayList;
import java.util.List;

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
	private final List<Reply> replies = new ArrayList<>();

	public void addReplies(List<Reply> replies) {
		this.replies.addAll(replies);
	}

	public boolean contain(Reply reply) {
		return replies.contains(reply);
	}
}
