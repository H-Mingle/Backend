package com.hyundai.hmingle.mapper.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReplyResponse {

	private Long id;
	private String nickname;
	private String content;
	private Long parentId;
	private int heartCount;
}
