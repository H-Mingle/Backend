package com.hyundai.hmingle.mapper;

import java.util.Optional;

import com.hyundai.hmingle.domain.post.Post;
import com.hyundai.hmingle.mapper.dto.request.PostCreateMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.PostDeleteMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.PostDetailMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.PostSidesMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.PostUpdateMapperRequest;
import com.hyundai.hmingle.mapper.dto.response.PostDetailMapperResponse;

public interface PostMapper {

	PostDetailMapperResponse getPostDetail(PostDetailMapperRequest postDetailMapperRequest);

	void getPostId(PostSidesMapperRequest postSidesMapperRequest);

	Optional<Post> findById(Long id);

	int findPostCountByMemberId(Long memberId);

	Long findMemberId(Long postId);

	Long save(PostCreateMapperRequest params);

	void updatePost(PostUpdateMapperRequest postUpdateMapperRequest);

	int upReadCount(Long postId);

	Long removePost(PostDeleteMapperRequest params);
}
