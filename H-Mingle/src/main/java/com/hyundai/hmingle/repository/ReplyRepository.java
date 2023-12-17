package com.hyundai.hmingle.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyundai.hmingle.domain.member.Member;
import com.hyundai.hmingle.domain.post.Post;
import com.hyundai.hmingle.domain.post.Reply;
import com.hyundai.hmingle.mapper.ReplyMapper;
import com.hyundai.hmingle.mapper.dto.request.RepliesMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.ReplyCreateMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.ReplyDeleteMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.ReplyUpdateMapperRequest;
import com.hyundai.hmingle.mapper.dto.response.ReplyCreateResponseDto;
import com.hyundai.hmingle.mapper.dto.response.ReplyResponse;
import com.hyundai.hmingle.support.DateTimeConvertor;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ReplyRepository {

	private final ReplyMapper replyMapper;
	private final DateTimeConvertor dateTimeConvertor;

	public Long save(Post post, Reply reply, Member member, Long parentId) {
		ReplyCreateMapperRequest replyCreateMapperRequest = new ReplyCreateMapperRequest(
			reply.getContent(), post.getId(), member.getId(), parentId, reply.getDepth(), dateTimeConvertor.current()
		);
		replyMapper.save(replyCreateMapperRequest);
		return replyCreateMapperRequest.getId();
	}

	public void update(Reply reply, String content) {
		ReplyUpdateMapperRequest replyUpdateMapperRequest = new ReplyUpdateMapperRequest(reply.getId(), content, dateTimeConvertor.current());
		replyMapper.update(replyUpdateMapperRequest);
	}

	public void delete(Long replyId) {
		ReplyDeleteMapperRequest replyDeleteMapperRequest = new ReplyDeleteMapperRequest(replyId, dateTimeConvertor.current());
		replyMapper.delete(replyDeleteMapperRequest);
	}

	public void deleteWithReplies(Long replyId) {
		ReplyDeleteMapperRequest replyDeleteMapperRequest = new ReplyDeleteMapperRequest(replyId, dateTimeConvertor.current());
		replyMapper.deleteWithReplies(replyDeleteMapperRequest);
	}

	public Reply findById(Long replyId) {
		Reply savedReply = replyMapper.findById(replyId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 댓글입니다."));
		if (savedReply.isRemoved()) {
			throw new RuntimeException("삭제된 댓글입니다.");
		}
		return savedReply;
	}

	public ReplyCreateResponseDto findSaved(Long replyId) {
		return replyMapper.findSaved(replyId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 댓글입니다."));
	}

	public Reply findParentByParentId(Long parentId) {
		if (parentId == null) {
			return null;
		}
		Reply parentReply = findByParentId(parentId);
		if (parentReply.getDepth() > 0) {
			throw new RuntimeException("댓글의 depth 는 최대 1 입니다.");
		}
		return parentReply;
	}

	public List<ReplyResponse> findAll(RepliesMapperRequest request) {
		if (request.getParentId() == null) {
			return replyMapper.findAllIfParentIsNull(request);
		}
		return replyMapper.findAll(request);
	}

	private Reply findByParentId(Long parentId) {
		Reply parentReply = replyMapper.findById(parentId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 상위 댓글입니다."));
		if (parentReply.isRemoved()) {
			throw new RuntimeException("삭제된 상위 댓글 입니다.");
		}
		return parentReply;
	}
}
