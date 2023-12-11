package com.hyundai.hmingle.mapper;

import java.util.Optional;

import com.hyundai.hmingle.domain.post.Reply;
import com.hyundai.hmingle.mapper.dto.request.ReplyCreateDto;

public interface ReplyMapper {

	Optional<Reply> findById(Long id);

	void save(ReplyCreateDto reply);
}
