package com.hyundai.hmingle.mapper.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostIdMapperResponse {
	private Long previousId;
	private Long subsequentId;
}
