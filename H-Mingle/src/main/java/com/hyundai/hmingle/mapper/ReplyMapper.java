package com.hyundai.hmingle.mapper;

import java.util.Optional;

import com.hyundai.hmingle.domain.post.Reply;
import com.hyundai.hmingle.mapper.dto.request.ReplyCreateDto;
import com.hyundai.hmingle.mapper.dto.request.ReplyUpdateDto;

public interface ReplyMapper {

	Optional<Reply> findById(Long id);

	void save(ReplyCreateDto reply);

	void update(ReplyUpdateDto reply);
}
