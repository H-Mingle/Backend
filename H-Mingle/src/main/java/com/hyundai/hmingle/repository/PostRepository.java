package com.hyundai.hmingle.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyundai.hmingle.controller.dto.request.PostUpdateRequest;
import com.hyundai.hmingle.domain.post.Post;
import com.hyundai.hmingle.domain.post.Reply;
import com.hyundai.hmingle.exception.MingleException;
import com.hyundai.hmingle.mapper.PostMapper;
import com.hyundai.hmingle.mapper.ReplyMapper;
import com.hyundai.hmingle.mapper.dto.request.PostCreateMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.PostDeleteMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.PostDetailMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.PostSidesMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.PostUpdateMapperRequest;
import com.hyundai.hmingle.mapper.dto.response.PostDetailMapperResponse;
import com.hyundai.hmingle.mapper.dto.response.PostSidesMapperResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PostRepository {

	private final PostMapper postMapper;
	private final ReplyMapper replyMapper;

	public Long save(String content, Long channelId, Long memberId) {
		PostCreateMapperRequest postCreateMapperRequest = new PostCreateMapperRequest(content, channelId, memberId);
		postMapper.save(postCreateMapperRequest);
		return postCreateMapperRequest.getPostId();
	}

	public PostDetailMapperResponse getPostDetail(Long postId, Long memberId) {
		PostDetailMapperRequest postDetailMapperRequest = new PostDetailMapperRequest(postId, memberId);
		return postMapper.getPostDetail(postDetailMapperRequest);
	}

	public PostSidesMapperResponse getPostId(Long postId) {
		PostSidesMapperRequest request = new PostSidesMapperRequest(postId);
		postMapper.getPostId(request);
		return new PostSidesMapperResponse(request.getPreviousId(), request.getSubsequentId());
	}

	public int findCountByMemberId(Long memberId) {
		return postMapper.findPostCountByMemberId(memberId);
	}

	public Long removePost(PostDeleteMapperRequest params) {
		return postMapper.removePost(params);
	}

	public Post findById(Long postId) {
		Post savedPost = postMapper.findById(postId)
			.orElseThrow(() -> new MingleException("존재하지 않는 게시글 입니다."));
		if (savedPost.isRemoved()) {
			throw new MingleException("삭제된 게시글 입니다.");
		}
		return savedPost;
	}

	public Post findWithRepliesById(Long postId) {
		Post post = findById(postId);
		List<Reply> replies = replyMapper.findAllByPostId(postId);
		post.addReplies(replies);
		return post;
	}

	public void updatePost(PostUpdateRequest params) {
		PostUpdateMapperRequest postUpdateMapperRequest = new PostUpdateMapperRequest(
			params.getPostId(), params.getContent(), params.getModifiedDate());
		postMapper.updatePost(postUpdateMapperRequest);
	}

	public void upReadCount(Long postId) {
		postMapper.upReadCount(postId);
	}

	public Long findMemberId(Long postId) {
		return postMapper.findMemberId(postId);
	}
}
