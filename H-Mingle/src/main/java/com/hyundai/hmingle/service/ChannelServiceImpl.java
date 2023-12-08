package com.hyundai.hmingle.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyundai.hmingle.controller.dto.response.ChannelGetResponse;
import com.hyundai.hmingle.mapper.ChannelMapper;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Log
@Service
@AllArgsConstructor
public class ChannelServiceImpl implements ChannelService {
	@Autowired
	private ChannelMapper mapper;
	
	public List<ChannelGetResponse> getList() {
		List<ChannelGetResponse> channels = mapper.getList();
		for(ChannelGetResponse channel:channels) {
			if(channel.getRecent() == null)
				channel.setRecent("�ۼ� �Խù��� �����ϴ�");
			else {
				Timestamp date = Timestamp.valueOf(channel.getRecent());
				Date modifiedTime = new Date(date.getTime());
				String recentTime = calculateTime(modifiedTime);

				channel.setRecent(recentTime);
			}		
		}
	
		return channels;
	}
	

	public static String calculateTime(Date date) {
		final int SEC = 60;
		final int MIN = 60;
		final int HOUR = 24;
		final int DAY = 30;
		final int MONTH = 12;
		
		long curTime = System.currentTimeMillis();
		long regTime = date.getTime();
		long diffTime = (curTime-regTime)/1000;
		
		String msg = null;
		
		if(diffTime < SEC) {
			msg = diffTime + "�� ��";
		} else if ((diffTime /= SEC) < MIN) {
			msg = diffTime + "�� ��";
		} else if ((diffTime /= MIN) < HOUR) {
			msg = (diffTime) + "�ð� ��";
		} else if ((diffTime /= HOUR) < DAY) {
			msg = (diffTime) + "�� ��";
		} else if ((diffTime /= DAY) < MONTH) {
			msg = (diffTime) + "�� ��";
		} else {
			msg = (diffTime) + "�� ��";
		}
		return msg;
	}

}
