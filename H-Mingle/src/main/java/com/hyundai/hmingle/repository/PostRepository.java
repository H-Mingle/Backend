package com.hyundai.hmingle.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hyundai.hmingle.controller.dto.request.PostUpdateRequest;
import com.hyundai.hmingle.domain.post.Post;
import com.hyundai.hmingle.domain.post.Reply;
import com.hyundai.hmingle.mapper.PostMapper;
import com.hyundai.hmingle.mapper.ReplyMapper;
import com.hyundai.hmingle.mapper.dto.request.PostCreateMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.PostDeleteMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.PostDetailMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.PostUpdateMapperRequest;
import com.hyundai.hmingle.mapper.dto.response.PostDetailMapperResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PostRepository {

	private final PostMapper postMapper;
	private final ReplyMapper replyMapper;

	public Long save(PostCreateMapperRequest params) {
		return postMapper.save(params);
	}

	public PostDetailMapperResponse getPostDetail(Long postId, Long memberId) {
		PostDetailMapperRequest postDetailMapperRequest = new PostDetailMapperRequest(postId, memberId);
		return postMapper.getPostDetail(postDetailMapperRequest);
	}

	public void getPostId(Map<String, BigDecimal> parameterMap) {
		postMapper.getPostId(parameterMap);
	}

	public Long removePost(PostDeleteMapperRequest params) {
		return postMapper.removePost(params);
	}

	public Post findById(Long postId) {
		Post savedPost = postMapper.findById(postId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 게시글 입니다."));
		if (savedPost.isRemoved()) {
			throw new RuntimeException("삭제된 게시글 입니다.");
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

	public List<Long> findPostByChannelId(Long channelId) {
		return postMapper.findPostByChannelId(channelId);
	}

	public int upReadCount(Long postId) {
		return postMapper.upReadCount(postId);
	}

	public Long findMemberId(Long postId) {
		return postMapper.findMemberId(postId);
	}
}
