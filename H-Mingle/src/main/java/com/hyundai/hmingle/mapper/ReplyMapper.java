package com.hyundai.hmingle.mapper;

import java.util.List;
import java.util.Optional;

import com.hyundai.hmingle.domain.post.Reply;
import com.hyundai.hmingle.mapper.dto.request.RepliesMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.ReplyCreateMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.ReplyDeleteMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.ReplyUpdateMapperRequest;
import com.hyundai.hmingle.mapper.dto.response.ReplyCreateMapperResponse;
import com.hyundai.hmingle.mapper.dto.response.ReplyMapperResponse;

public interface ReplyMapper {

	Optional<Reply> findById(Long id);

	Optional<ReplyCreateMapperResponse> findSaved(Long id);

	List<Reply> findAllByPostId(Long postId);

	List<ReplyMapperResponse> findAll(RepliesMapperRequest request);

	List<ReplyMapperResponse> findAllIfParentIsNull(RepliesMapperRequest request);

	void save(ReplyCreateMapperRequest reply);

	void update(ReplyUpdateMapperRequest reply);

	void delete(ReplyDeleteMapperRequest replyDeleteMapperRequest);

	void deleteWithReplies(ReplyDeleteMapperRequest replyDeleteMapperRequest);
}
