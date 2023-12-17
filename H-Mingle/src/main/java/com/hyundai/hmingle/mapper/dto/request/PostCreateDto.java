package com.hyundai.hmingle.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostCreateDto {

    private final Long postId;
    private final String content;
    private final Long channelId;
    private final Long memberId;
}
