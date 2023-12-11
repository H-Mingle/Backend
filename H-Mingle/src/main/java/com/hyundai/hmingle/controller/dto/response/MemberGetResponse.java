package com.hyundai.hmingle.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberGetResponse {
	private Long memberId;
	private String email;
	private String nickname;
	private String introduction;
	private String image;
}
