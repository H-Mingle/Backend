package com.hyundai.hmingle.controller.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberUpdateRequest {
    private Long memberId;
    private String nickname;
    private String introduction;
}
