package com.hyundai.hmingle.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundai.hmingle.controller.dto.request.ReplyCreateRequest;
import com.hyundai.hmingle.controller.dto.request.ReplyUpdateRequest;
import com.hyundai.hmingle.controller.dto.response.ReplyCreateResponse;
import com.hyundai.hmingle.controller.dto.response.ReplyUpdateResponse;
import com.hyundai.hmingle.domain.member.Member;
import com.hyundai.hmingle.domain.post.Post;
import com.hyundai.hmingle.domain.post.Reply;
import com.hyundai.hmingle.repository.MemberRepository;
import com.hyundai.hmingle.repository.PostRepository;
import com.hyundai.hmingle.repository.ReplyRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ReplyService {

	private final MemberRepository memberRepository;
	private final PostRepository postRepository;
	private final ReplyRepository replyRepository;

	public ReplyCreateResponse save(Long memberId, Long postId, ReplyCreateRequest request) {
		Member savedMember = memberRepository.findById(memberId);
		Post savedPost = postRepository.findById(postId);
		Reply savedParentReply = replyRepository.findParentById(request);

		Reply reply = new Reply(request.getContent(), savedParentReply, savedMember);
		Long parentId = findParentId(savedParentReply);
		Long replyId = replyRepository.save(savedPost, reply, savedMember, parentId);

		return new ReplyCreateResponse(savedPost.getId(), replyId, reply.getContent(), parentId);
	}

	public ReplyUpdateResponse update(Long memberId, Long postId, Long replyId, ReplyUpdateRequest request) {
		Member savedMember = memberRepository.findById(memberId);
		Post savedPost = postRepository.findWithRepliesById(postId);
		Reply savedReply = replyRepository.findById(replyId);

		validateReplyBelongToPost(savedPost, savedReply);
		validateMemberIsWriter(savedReply, savedMember);

		replyRepository.update(savedReply, request.getContent());
		return new ReplyUpdateResponse(savedPost.getId(), savedReply.getId(), request.getContent());
	}

	private void validateReplyBelongToPost(Post savedPost, Reply savedReply) {
		if (!savedPost.contain(savedReply)) {
			throw new RuntimeException("해당 게시글에 존재하지 않는 댓글입니다.");
		}
	}

	private void validateMemberIsWriter(Reply savedReply, Member savedMember) {
		if (!savedReply.isWriter(savedMember)) {
			throw new RuntimeException("작성자가 아닙니다.");
		}
	}

	private Long findParentId(Reply parent) {
		if (parent == null) {
			return null;
		}
		return parent.getId();
	}
}
