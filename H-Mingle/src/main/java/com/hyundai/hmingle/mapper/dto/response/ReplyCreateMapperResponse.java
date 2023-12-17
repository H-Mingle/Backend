package com.hyundai.hmingle.mapper.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplyCreateMapperResponse {

	private Long id;
	private String content;
	private int depth;
	private Long memberId;
	private String nickname;
	private LocalDateTime createdDate;
	private String imageUrl;
}
