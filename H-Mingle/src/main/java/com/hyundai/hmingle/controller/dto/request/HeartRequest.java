
package com.hyundai.hmingle.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HeartRequest {
	private Long postId;
	private Long memberId;
}
