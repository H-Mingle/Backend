package com.hyundai.hmingle.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostUpdateRequest {
    private Long postId;
    private String content;
}
