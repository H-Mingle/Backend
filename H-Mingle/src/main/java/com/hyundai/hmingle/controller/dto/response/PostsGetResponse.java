package com.hyundai.hmingle.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PostsGetResponse {
    private String channelName;
    private boolean isNext;
    private List<PostListGetResponse> posts;

}
