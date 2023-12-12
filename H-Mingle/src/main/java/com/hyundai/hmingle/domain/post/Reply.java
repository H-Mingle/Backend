package com.hyundai.hmingle.domain.post;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.hyundai.hmingle.domain.member.Member;
import com.hyundai.hmingle.domain.common.Base;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Reply extends Base {

	private String content;
	private int depth = 0;
	private Reply parent;
	private Member member;

	public Reply(String content, Reply reply, Member member) {
		this.content = content;
		this.member = member;
		if (reply != null) {
			this.depth = reply.getDepth() + 1;
			this.parent = reply;
		}
	}

	public boolean isWriter(Member member) {
		return this.member.isSame(member);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Reply reply = (Reply)o;
		return Objects.equals(id, reply.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
