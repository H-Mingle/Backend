package com.hyundai.hmingle.mapper;

import java.util.List;
import java.util.Optional;

import com.hyundai.hmingle.domain.post.Reply;
import com.hyundai.hmingle.mapper.dto.request.RepliesMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.ReplyCreateMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.ReplyDeleteMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.ReplyUpdateMapperRequest;
import com.hyundai.hmingle.mapper.dto.response.ReplyCreateResponseDto;
import com.hyundai.hmingle.mapper.dto.response.ReplyResponse;

public interface ReplyMapper {

	Optional<Reply> findById(Long id);

	Optional<ReplyCreateResponseDto> findSaved(Long id);

	List<Reply> findAllByPostId(Long postId);

	List<ReplyResponse> findAll(RepliesMapperRequest request);

	List<ReplyResponse> findAllIfParentIsNull(RepliesMapperRequest request);

	void save(ReplyCreateMapperRequest reply);

	void update(ReplyUpdateMapperRequest reply);

	void delete(ReplyDeleteMapperRequest replyDeleteMapperRequest);

	void deleteWithReplies(ReplyDeleteMapperRequest replyDeleteMapperRequest);
}
