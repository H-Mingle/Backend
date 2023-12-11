package com.hyundai.hmingle.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hyundai.hmingle.domain.post.Post;
import com.hyundai.hmingle.domain.post.Reply;
import com.hyundai.hmingle.mapper.PostMapper;
import com.hyundai.hmingle.mapper.ReplyMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PostRepository {

	private final PostMapper postMapper;
	private final ReplyMapper replyMapper;

	public Post findById(Long postId) {
		return postMapper.findById(postId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 게시글 입니다."));
	}

	public Post findWithRepliesById(Long postId) {
		Post post = findById(postId);
		List<Reply> replies = replyMapper.findAllByPostId(postId);
		post.addReplies(replies);
		return post;
	}
}
