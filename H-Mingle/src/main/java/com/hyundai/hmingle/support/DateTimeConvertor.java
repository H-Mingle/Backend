package com.hyundai.hmingle.support;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DateTimeConvertor {

	private static final int SEC = 60;
	private static final int MIN = 60;
	private static final int HOUR = 24;
	private static final int DAY = 30;
	private static final int MONTH = 12;

	public String calculate(LocalDateTime date, String messageForNull) {
		if (date == null) {
			return messageForNull;
		}
		LocalDateTime now = LocalDateTime.now();
		long diffTime = date.until(now, ChronoUnit.SECONDS);

		return convert(diffTime);
	}

	public LocalDateTime current() {
		return ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
	}

	private String convert(long diffTime) {
		if (diffTime < SEC) {
			return diffTime + "초 전";
		} else if ((diffTime /= SEC) < MIN) {
			return diffTime + "분 전";
		} else if ((diffTime /= MIN) < HOUR) {
			return diffTime + "시간 전";
		} else if ((diffTime /= HOUR) < DAY) {
			return diffTime + "일 전";
		} else if ((diffTime /= DAY) < MONTH) {
			return diffTime + "달 전";
		}
		return diffTime + "년 전";
	}
}
