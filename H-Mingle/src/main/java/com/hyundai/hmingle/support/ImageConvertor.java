package com.hyundai.hmingle.support;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import com.hyundai.hmingle.exception.MingleException;

@Component
public class ImageConvertor {

	public byte[] convertPath(String path) {
		try (InputStream imageStream = new FileInputStream(path)) {
			return IOUtils.toByteArray(imageStream);
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			throw new MingleException("이미지를 불러오는데 실패히였습니다.");
		}
	}
}
