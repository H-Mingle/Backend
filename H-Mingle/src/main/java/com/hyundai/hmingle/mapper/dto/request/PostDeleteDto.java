package com.hyundai.hmingle.mapper.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostDeleteDto {
    Long postId;
    Long memberId;
}
