package com.hyundai.hmingle.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundai.hmingle.controller.dto.request.ReplyCreateRequest;
import com.hyundai.hmingle.controller.dto.request.ReplyUpdateRequest;
import com.hyundai.hmingle.controller.dto.response.ReplyCreateResponse;
import com.hyundai.hmingle.controller.dto.response.ReplyDetailResponse;
import com.hyundai.hmingle.controller.dto.response.ReplyUpdateResponse;
import com.hyundai.hmingle.domain.member.Member;
import com.hyundai.hmingle.domain.post.Post;
import com.hyundai.hmingle.domain.post.Reply;
import com.hyundai.hmingle.mapper.dto.request.RepliesRequest;
import com.hyundai.hmingle.mapper.dto.response.ReplyResponse;
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
		Reply savedParentReply = replyRepository.findParentByParentId(request.getParentId());

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

	public void delete(Long memberId, Long postId, Long replyId) {
		Member savedMember = memberRepository.findById(memberId);
		Post savedPost = postRepository.findWithRepliesById(postId);
		Reply savedReply = replyRepository.findById(replyId);

		validateReplyBelongToPost(savedPost, savedReply);
		validateMemberIsWriter(savedReply, savedMember);

		delete(savedReply);
	}

	public List<ReplyDetailResponse> findAllWithPaging(Long postId, Integer page, Integer size, Long parentId) {
		validatePageIsNotNegative(page);
		validateSizeIsNotNegative(size);

		Post savedPost = postRepository.findById(postId);

		RepliesRequest request = new RepliesRequest(savedPost.getId(), parentId);
		List<ReplyResponse> replies = replyRepository.findAll(request);

		return replies.stream()
			.map(reply -> new ReplyDetailResponse(
				reply.getId(), reply.getNickname(), reply.getContent(), 0, "", null))
			.collect(Collectors.toUnmodifiableList());
	}

	private void delete(Reply savedReply) {
		if (savedReply.isRoot()) {
			replyRepository.deleteWithReplies(savedReply.getId());
		} else {
			replyRepository.delete(savedReply.getId());
		}
	}

	private void validatePageIsNotNegative(Integer page) {
		if (page <= 0) {
			throw new RuntimeException("page 는 1보다 커야합니다.");
		}
	}

	private void validateSizeIsNotNegative(Integer size) {
		if (size <= 0) {
			throw new RuntimeException("size 는 1보다 커야합니다.");
		}
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
