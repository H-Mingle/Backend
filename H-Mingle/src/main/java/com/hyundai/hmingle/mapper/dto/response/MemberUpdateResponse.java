package com.hyundai.hmingle.mapper.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberUpdateResponse {
	private Long memberId;
	private String email;
	private String nickname;
	private String introduction;

	private String modifiedDate;

	public MemberUpdateResponse(Long memberId, String email, String nickname, String introduction,
		String modifiedDate) {
		this.memberId = memberId;
		this.email = email;
		this.nickname = nickname;
		this.introduction = introduction;
		this.modifiedDate = modifiedDate;
	}
}
