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

	@Transactional(readOnly = true)
	public MyPostsResponse findPostsWrittenMember(Long memberId, Integer requestPage, Integer requestSize) {
		int page = validatePageIsNotNegative(requestPage);
		int size = validateSizeIsNotNegative(requestSize);
		int startRow = calculateStartRow(page, size);

		Member savedMember = memberRepository.findById(memberId);
		List<MyPostResponse> responses = imageRepository.findImageUrlsByMemberId(savedMember.getId(), startRow, size + 1);
		return convertMyPostsResponse(size, responses);
	}

	@Transactional(readOnly = true)
	public MyPostsResponse findPostsLikedMember(Long memberId, Integer requestPage, Integer requestSize) {
		int page = validatePageIsNotNegative(requestPage);
		int size = validateSizeIsNotNegative(requestSize);
		int startRow = calculateStartRow(page, size);

		Member savedMember = memberRepository.findById(memberId);
		List<MyPostResponse> responses = imageRepository.findImageUrlLikedByMemberId(savedMember.getId(), startRow, size + 1);
		return convertMyPostsResponse(size, responses);
	}

	private MyPostsResponse convertMyPostsResponse(int size, List<MyPostResponse> responses) {
		boolean hasNext = hasNext(size, responses.size());
		if (hasNext) {
			responses.remove(responses.size() - 1);
		}
		return new MyPostsResponse(hasNext,
			responses.stream()
				.map(response -> new PostsResponse(response.getId(), imageConvertor.convertPath(response.getImageUrl())))
				.collect(Collectors.toUnmodifiableList()));
	}

	private int calculateStartRow(int page, int size) {
		return (page - 1) * size;
	}

	private boolean hasNext(int requestSize, int responseSize) {
		return responseSize > requestSize;
	}

	private int validatePageIsNotNegative(Integer page) {
		if (page == null) {
			throw new RuntimeException("page 를 입력해주세요.");
		}
		if (page <= 0) {
			throw new RuntimeException("page 는 1보다 커야합니다.");
		}
		return page;
	}

	private int validateSizeIsNotNegative(Integer size) {
		if (size == null) {
			throw new RuntimeException("size 를 입력해주세요.");
		}
		if (size <= 0) {
			throw new RuntimeException("size 는 1보다 커야합니다.");
		}
		return size;
	}
}
