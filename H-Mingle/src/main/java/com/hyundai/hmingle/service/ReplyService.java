package com.hyundai.hmingle.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundai.hmingle.controller.dto.request.ReplyCreateRequest;
import com.hyundai.hmingle.controller.dto.request.ReplyUpdateRequest;
import com.hyundai.hmingle.controller.dto.response.ReplyCreateResponse;
import com.hyundai.hmingle.controller.dto.response.ReplyUpdateResponse;
import com.hyundai.hmingle.domain.member.Member;
import com.hyundai.hmingle.domain.post.Post;
import com.hyundai.hmingle.domain.post.Reply;
import com.hyundai.hmingle.mapper.MemberMapper;
import com.hyundai.hmingle.mapper.PostMapper;
import com.hyundai.hmingle.mapper.ReplyMapper;
import com.hyundai.hmingle.mapper.dto.request.ReplyCreateDto;
import com.hyundai.hmingle.mapper.dto.request.ReplyUpdateDto;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ReplyService {

	private final MemberMapper memberMapper;
	private final PostMapper postMapper;
	private final ReplyMapper replyMapper;

	public ReplyCreateResponse save(Long memberId, Long postId, ReplyCreateRequest request) {
		Member savedMember = findMemberById(memberId);
		Post savedPost = findPostById(postId);
		Reply savedParentReply = findParentReplyById(request);

		Reply reply = new Reply(request.getContent(), savedParentReply, savedMember);
		Long parentId = findParentId(savedParentReply);
		ReplyCreateDto replyCreateDto = new ReplyCreateDto(
			reply.getContent(), savedPost.getId(), savedMember.getId(), parentId, reply.getDepth()
		);
		replyMapper.save(replyCreateDto);
		return new ReplyCreateResponse(
			savedPost.getId(), replyCreateDto.getId(), reply.getContent(), parentId
		);
	}

	public ReplyUpdateResponse update(Long memberId, Long postId, Long replyId, ReplyUpdateRequest request) {
		Member savedMember = findMemberById(memberId);
		Post savedPost = findPostWithRepliesById(postId);
		Reply savedReply = findReplyById(replyId);

		if (!savedPost.contain(savedReply)) {
			throw new RuntimeException("해당 게시글에 존재하지 않는 댓글입니다.");
		}

		if (!savedReply.isWriter(savedMember)) {
			throw new RuntimeException("작성자가 아닙니다.");
		}

		ReplyUpdateDto replyUpdateDto = new ReplyUpdateDto(savedReply.getId(), request.getContent());
		replyMapper.update(replyUpdateDto);
		return new ReplyUpdateResponse(savedPost.getId(), savedReply.getId(), replyUpdateDto.getContent());
	}

	private Member findMemberById(Long memberId) {
		return memberMapper.findById(memberId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 계정 입니다."));
	}

	private Post findPostById(Long postId) {
		return postMapper.findById(postId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 게시글 입니다."));
	}

	private Post findPostWithRepliesById(Long postId) {
		Post post = findPostById(postId);
		List<Reply> replies = replyMapper.findAllByPostId(postId);
		post.addReplies(replies);
		return post;
	}

	private Reply findReplyById(Long replyId) {
		return replyMapper.findById(replyId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 댓글입니다."));
	}

	private Reply findParentReplyById(ReplyCreateRequest request) {
		if (request.getParentId() == null) {
			return null;
		}
		Reply parentReply = findReplyByParentId(request);
		if (parentReply.getDepth() > 0) {
			throw new RuntimeException("댓글의 depth 는 최대 1 입니다.");
		}
		return parentReply;
	}

	private Reply findReplyByParentId(ReplyCreateRequest request) {
		return replyMapper.findById(request.getParentId())
			.orElseThrow(() -> new RuntimeException("존재하지 않는 상위 댓글입니다."));
	}

	private Long findParentId(Reply parent) {
		if (parent == null) {
			return null;
		}
		return parent.getId();
	}
}
