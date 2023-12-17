package com.hyundai.hmingle.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;

import com.hyundai.hmingle.controller.dto.request.PostUpdateRequest;
import com.hyundai.hmingle.controller.dto.response.PostGetResponse;
import com.hyundai.hmingle.domain.post.Post;
import com.hyundai.hmingle.mapper.dto.request.PostCreateMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.PostDeleteMapperRequest;
import com.hyundai.hmingle.mapper.dto.response.PostDetailMapperResponse;

public interface PostMapper {

	Long save(PostCreateMapperRequest params);

	PostDetailMapperResponse getPostDetail(@Param("postId") Long postId, @Param("memberId") Long memberId);

	void getPostId(Map<String, BigDecimal> map);

	Long removePost(PostDeleteMapperRequest params);

	Optional<Post> findById(Long id);

	int findPostCountByMemberId(Long memberId);

	void updatePost(PostUpdateRequest params);

	List<Long> findPostByChannelId(Long channelId);

	int upReadCount(Long postId);

	Long findMemberId(Long postId);
}
