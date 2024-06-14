package com.unknown.paldak.admin.util;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

@Component
public class AdminUploadPathConfig {
//	
//	private String CURRENT_DIRECTORY = System.getProperty("user.home");
//	
	public String getUploadPath(){

//        Path projectRoot = Paths.get(CURRENT_DIRECTORY);
//
//        Path uploadFolderPath = projectRoot.resolve(CURRENT_DIRECTORY + "/paldak/upload");
//
//        String uploadFolder = uploadFolderPath.toString();
		
		String uploadFolder = "C:\\upload";
       
        return uploadFolder;
    }
}
