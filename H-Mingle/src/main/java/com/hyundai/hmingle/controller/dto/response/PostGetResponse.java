package com.hyundai.hmingle.controller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostGetResponse {

	private final Long postId;
	private final String content;
	private final int readCount;
	private final String nickname;
	private final int heartCount;
	private final String channelName;
	private final String createdDate;
	private final Long previousId;
	private final Long subsequentId;
	private final boolean liked;
	private final byte[] myImage;
	private final byte[] writerImage;
}