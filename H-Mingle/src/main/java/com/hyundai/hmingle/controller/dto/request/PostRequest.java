package com.hyundai.hmingle.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostRequest {
    private Long postId;
    private Long memberId;
}
