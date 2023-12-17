package com.hyundai.hmingle.mapper;

import java.util.List;
import java.util.Optional;

import com.hyundai.hmingle.domain.post.Reply;
import com.hyundai.hmingle.mapper.dto.request.RepliesRequest;
import com.hyundai.hmingle.mapper.dto.request.ReplyCreateDto;
import com.hyundai.hmingle.mapper.dto.request.ReplyDeleteDto;
import com.hyundai.hmingle.mapper.dto.request.ReplyUpdateDto;
import com.hyundai.hmingle.mapper.dto.response.ReplyCreateResponseDto;
import com.hyundai.hmingle.mapper.dto.response.ReplyResponse;

public interface ReplyMapper {

	Optional<Reply> findById(Long id);

	Optional<ReplyCreateResponseDto> findSaved(Long id);

	List<Reply> findAllByPostId(Long postId);

	List<ReplyResponse> findAll(RepliesRequest request);

	List<ReplyResponse> findAllIfParentIsNull(RepliesRequest request);

	void save(ReplyCreateDto reply);

	void update(ReplyUpdateDto reply);

	void delete(ReplyDeleteDto replyDeleteDto);

	void deleteWithReplies(ReplyDeleteDto replyDeleteDto);
}
