package com.hyundai.hmingle.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundai.hmingle.controller.dto.response.ChannelGetResponse;
import com.hyundai.hmingle.repository.ChannelRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ChannelService {

	private final ChannelRepository channelRepository;

	@Transactional(readOnly = true)
	public List<ChannelGetResponse> getList() {
		List<ChannelGetResponse> channels = channelRepository.getList();
		for (ChannelGetResponse channel : channels) {
			if (channel.getRecent() == null)
				channel.setRecent("작성 게시물이 없습니다.");
			else {
				Timestamp date = Timestamp.valueOf(channel.getRecent());
				Date modifiedTime = new Date(date.getTime());
				String recentTime = calculateTime(modifiedTime);

				channel.setRecent(recentTime);
			}
		}
		return channels;
	}

	private String calculateTime(Date date) {

		final int SEC = 60;
		final int MIN = 60;
		final int HOUR = 24;
		final int DAY = 30;
		final int MONTH = 12;

		long curTime = System.currentTimeMillis();
		long regTime = date.getTime();
		long diffTime = (curTime - regTime) / 1000;

		String msg = null;

		if (diffTime < SEC) {
			msg = diffTime + "초 전";
		} else if ((diffTime /= SEC) < MIN) {
			msg = diffTime + "분 전";
		} else if ((diffTime /= MIN) < HOUR) {
			msg = (diffTime) + "시간 전";
		} else if ((diffTime /= HOUR) < DAY) {
			msg = (diffTime) + "일 전";
		} else if ((diffTime /= DAY) < MONTH) {
			msg = (diffTime) + "달 전";
		} else {
			msg = (diffTime) + "년 전";
		}
		return msg;
	}
}
