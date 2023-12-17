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

	PostDetailMapperResponse getPostDetail(@Param("postId") Long postId, @Param("memberId") Long memberId);

	void getPostId(Map<String, BigDecimal> map);

	Optional<Post> findById(Long id);

	int findPostCountByMemberId(Long memberId);

	List<Long> findPostByChannelId(Long channelId);

	Long findMemberId(Long postId);

	Long save(PostCreateMapperRequest params);

	void updatePost(PostUpdateRequest params);

	int upReadCount(Long postId);

	Long removePost(PostDeleteMapperRequest params);
}
