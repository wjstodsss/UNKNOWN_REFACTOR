package com.unknown.util;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FileUploadManager {
	private final UploadPathConfig uploadPathConfig;
	
    public String uploadFiles(MultipartFile[] uploadFiles) {
    	
    	uploadPathConfig.getUploadPath();
    	
        File uploadDir = new File(uploadPathConfig.getUploadPath());
        
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        
        StringBuilder imageURLs = new StringBuilder(); // 업로드된 파일 경로들을 저장할 StringBuilder
        
        for (MultipartFile multipartFile : uploadFiles) {
            String originalFilename = multipartFile.getOriginalFilename();
            String uploadedFilePath = uploadDir + "/" + originalFilename;
            
            try { 
                multipartFile.transferTo(new File(uploadedFilePath)); // 파일을 업로드
                imageURLs.append(originalFilename).append(";"); // 업로드된 파일의 경로를 StringBuilder에 추가
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return imageURLs.toString(); // 업로드된 파일들의 경로를 반환
    }
}