package com.hyundai.hmingle.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundai.hmingle.controller.dto.response.MyPostsResponse;
import com.hyundai.hmingle.controller.dto.response.PostsResponse;
import com.hyundai.hmingle.domain.member.Member;
import com.hyundai.hmingle.mapper.dto.response.MyPostResponse;
import com.hyundai.hmingle.repository.ImageRepository;
import com.hyundai.hmingle.repository.MemberRepository;
import com.hyundai.hmingle.support.ImageConvertor;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MyPageService {

	private final MemberRepository memberRepository;
	private final ImageRepository imageRepository;
	private final ImageConvertor imageConvertor;

	public MyPostsResponse findPostsWrittenMember(Long memberId, Integer page, Integer size) {
		Member savedMember = memberRepository.findById(memberId);
		List<MyPostResponse> responses = imageRepository.findImageUrlsByMemberId(savedMember.getId());
		return new MyPostsResponse(false,
			responses.stream()
				.map(response -> new PostsResponse(response.getId(), imageConvertor.convertPath(response.getImageUrl())))
				.collect(Collectors.toUnmodifiableList()));
	}
}
