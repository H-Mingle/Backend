package com.hyundai.hmingle.support;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

@Component
public class ImageConvertor {

	public byte[] convertPath(String path) {
		try (InputStream imageStream = new FileInputStream(path)) {
			return IOUtils.toByteArray(imageStream);
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			throw new RuntimeException("이미지를 불러오는데 실패히였습니다.");
		}
	}

	public List<byte[]> convertPaths(List<String> paths) {
		return paths.stream()
			.map(this::convertPath)
			.collect(Collectors.toUnmodifiableList());
	}
}
