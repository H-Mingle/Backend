package com.hyundai.hmingle.mapper.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostCreateDto {
    private Long postId;
    private String content;
    private Long channelId;
    private Long memberId;
}
