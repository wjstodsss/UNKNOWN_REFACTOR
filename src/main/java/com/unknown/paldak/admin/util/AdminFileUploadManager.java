package com.unknown.paldak.admin.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Log4j
@Component
@RequiredArgsConstructor
public class AdminFileUploadManager {
	private final AdminUploadPathConfig uploadPathConfig;
	
    public Map<String, String> uploadFiles(MultipartFile[] uploadFiles) {
    	
    	Map<String, String> map = new HashMap<>();

        File uploadDir = new File(uploadPathConfig.getUploadPath());
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        
        StringBuilder imageURLs = new StringBuilder(); // ??�????? ???? 경�??��?? ???��?? StringBuilder
        
        for (MultipartFile multipartFile : uploadFiles) {
        	
        	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = dateFormat.format(new Date());
            String datePath = currentDate.replace("-", "/");
            System.out.println("datePath = " + datePath );
            File dateDir = new File(uploadDir, datePath);
            if (!dateDir.exists()) {
                dateDir.mkdirs();
            }
            
            
            
            String originalFilename = multipartFile.getOriginalFilename();
      
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uuid = UUID.randomUUID().toString();
            String savedFilename = uuid + "_" + originalFilename;
            String uploadedFilePath = dateDir + "/" + savedFilename;
            String uploadedFilePathT = dateDir + "/t_" + savedFilename;
            
            log.info("originalFilename = " + originalFilename);
            log.info("extension = " + extension);
            log.info("uuid = " + uuid);
            log.info("savedFilename = " + savedFilename);
 
            map.put("uuid", uuid);
            map.put("uuidT", "t_" + uuid);
            map.put("originalFilename", originalFilename);
            map.put("datePath", datePath);
            
            log.info("originalFilename = " + map.get("originalFilename"));
            log.info("uuid = " + map.get("uuid"));
            log.info("datePath = " + map.get("datePath"));
          
            try { 
            	multipartFile.transferTo(new File(uploadedFilePath)); // ???��?? ??�???
            	FileOutputStream thumbnail = new FileOutputStream(new File(uploadedFilePathT)); // ???��?? ??�???
            	Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
            	
                imageURLs.append(datePath).append("/").append(savedFilename).append(";"); // ??�????? ???��?? 경�?�? StringBuilder?? �?�?
                map.put("imageURLs", imageURLs.toString());
                log.info("imageURLs = " + imageURLs );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return map;
    }
    
    public boolean deleteFile(String imageUrl) {
        String filePath = uploadPathConfig.getUploadPath() + "/" + imageUrl;
        
        log.info(uploadPathConfig.getUploadPath() + "/" + imageUrl);
        File file = new File(filePath);

        if (file.exists()) {
            return file.delete();
        } else {
            return false;
        }
    }

}