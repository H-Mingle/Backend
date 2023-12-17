package com.hyundai.hmingle.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.hyundai.hmingle.domain.post.Post;
import com.hyundai.hmingle.mapper.dto.request.PostCreateMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.PostDeleteMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.PostDetailMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.PostUpdateMapperRequest;
import com.hyundai.hmingle.mapper.dto.response.PostDetailMapperResponse;

public interface PostMapper {

	PostDetailMapperResponse getPostDetail(PostDetailMapperRequest postDetailMapperRequest);

	void getPostId(Map<String, BigDecimal> map);

	Optional<Post> findById(Long id);

	int findPostCountByMemberId(Long memberId);

	List<Long> findPostByChannelId(Long channelId);

	Long findMemberId(Long postId);

	Long save(PostCreateMapperRequest params);

	void updatePost(PostUpdateMapperRequest postUpdateMapperRequest);

	int upReadCount(Long postId);

	Long removePost(PostDeleteMapperRequest params);
}
