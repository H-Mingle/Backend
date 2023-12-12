package com.hyundai.hmingle.repository;

import org.springframework.stereotype.Repository;

import com.hyundai.hmingle.controller.dto.request.ReplyCreateRequest;
import com.hyundai.hmingle.domain.member.Member;
import com.hyundai.hmingle.domain.post.Post;
import com.hyundai.hmingle.domain.post.Reply;
import com.hyundai.hmingle.mapper.ReplyMapper;
import com.hyundai.hmingle.mapper.dto.request.ReplyCreateDto;
import com.hyundai.hmingle.mapper.dto.request.ReplyUpdateDto;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ReplyRepository {

	private final ReplyMapper replyMapper;

	public Long save(Post post, Reply reply, Member member, Long parentId) {
		ReplyCreateDto replyCreateDto = new ReplyCreateDto(
			reply.getContent(), post.getId(), member.getId(), parentId, reply.getDepth()
		);
		replyMapper.save(replyCreateDto);
		return replyCreateDto.getId();
	}

	public void update(Reply reply, String content) {
		ReplyUpdateDto replyUpdateDto = new ReplyUpdateDto(reply.getId(), content);
		replyMapper.update(replyUpdateDto);
	}

	public void delete(Long replyId) {
		replyMapper.delete(replyId);
	}

	public void deleteWithReplies(Long replyId) {
		replyMapper.deleteWithReplies(replyId);
	}

	public Reply findById(Long replyId) {
		return replyMapper.findById(replyId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 댓글입니다."));
	}

	public Reply findParentById(ReplyCreateRequest request) {
		if (request.getParentId() == null) {
			return null;
		}
		Reply parentReply = findByParentId(request);
		if (parentReply.getDepth() > 0) {
			throw new RuntimeException("댓글의 depth 는 최대 1 입니다.");
		}
		return parentReply;
	}

	private Reply findByParentId(ReplyCreateRequest request) {
		return replyMapper.findById(request.getParentId())
			.orElseThrow(() -> new RuntimeException("존재하지 않는 상위 댓글입니다."));
	}
}
