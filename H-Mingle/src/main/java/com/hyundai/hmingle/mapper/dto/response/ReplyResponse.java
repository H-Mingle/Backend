package com.hyundai.hmingle.mapper.dto.response;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReplyResponse {

	private Long id;
	private Long memberId;
	private String nickname;
	private String content;
	private Long parentId;
	private int heartCount;
	private LocalDateTime createDate;
	private String imageUrl;
}
