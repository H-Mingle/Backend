package com.hyundai.hmingle.domain.post;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.hyundai.hmingle.controller.dto.request.ImageCreateRequest;

import lombok.extern.java.Log;

@Component
@PropertySource("classpath:app.properties")
public class ImageUtils {

	@Value("${uploadPath}")
    private String uploadPath;

    public List<ImageCreateRequest> uploadFiles(List<MultipartFile> multipartFiles) {
    	
        List<ImageCreateRequest> files = new ArrayList<>();
        int orders = 1;
        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile.isEmpty()) {
                continue;
            }
            files.add(uploadFile(orders++, multipartFile));

        }
        System.out.println(files.size());
        return files;
    }

   
    public ImageCreateRequest uploadFile(int orders, MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String saveName = generateSaveFilename(multipartFile.getOriginalFilename());
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")).toString();
        String uploadPath = getUploadPath(today) + File.separator + saveName;
        File uploadFile = new File(uploadPath);

        try {
            multipartFile.transferTo(uploadFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        ImageCreateRequest createdImage = new ImageCreateRequest(multipartFile.getOriginalFilename(),
        														 uploadPath, 
        														 multipartFile.getSize());
        createdImage.setSequence(orders);
        return createdImage;
    }

    
    private String generateSaveFilename(final String filename) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = StringUtils.getFilenameExtension(filename);
        return uuid + "." + extension;
    }

   
    private String getUploadPath() {
        return makeDirectories(uploadPath);
    }

   
    private String getUploadPath(final String addPath) {
        return makeDirectories(uploadPath + File.separator + addPath);
    }

   
    private String makeDirectories(final String path) {
        File dir = new File(path);
        if (dir.exists() == false) {
            dir.mkdirs();
        }
        return dir.getPath();
    }

}
