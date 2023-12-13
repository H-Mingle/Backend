package com.hyundai.hmingle.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hyundai.hmingle.controller.dto.request.PostUpdateRequest;
import com.hyundai.hmingle.mapper.dto.request.PostCreateDto;
import com.hyundai.hmingle.mapper.dto.request.PostDeleteDto;
import org.springframework.stereotype.Repository;

import com.hyundai.hmingle.domain.post.Post;
import com.hyundai.hmingle.domain.post.Reply;
import com.hyundai.hmingle.mapper.PostMapper;
import com.hyundai.hmingle.mapper.ReplyMapper;
import com.hyundai.hmingle.mapper.dto.response.PostDetailResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PostRepository {

	private final PostMapper postMapper;
	private final ReplyMapper replyMapper;

	public Long save(PostCreateDto params) {
		return postMapper.save(params);
	}

	public PostDetailResponse getPostDetail(Long postId) {
		return postMapper.getPostDetail(postId);
	}

	public void getPostId(Map<String, BigDecimal> parameterMap) {
		postMapper.getPostId(parameterMap);
	}

	public Long removePost(PostDeleteDto params) {
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

	public void updatePost(PostUpdateRequest params){
		postMapper.updatePost(params);
	};

	public List<Long> findPostByChannelId(Long channelId){
		return postMapper.findPostByChannelId(channelId);
	}
}
