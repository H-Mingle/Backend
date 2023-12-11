package com.hyundai.hmingle.mapper.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostDetailResponse {
    private Long postId;
    private String title;
    private String content;
    private int readCount;
    private String nickname;
    private Integer heartCount;
}