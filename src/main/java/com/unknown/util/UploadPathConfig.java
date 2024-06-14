package com.unknown.util;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

@Component
public class UploadPathConfig {
	
private String CURRENT_DIRECTORY = System.getProperty("user.home");
	
	public String getUploadPath(){

        Path projectRoot = Paths.get(CURRENT_DIRECTORY);

        Path uploadFolderPath = projectRoot.resolve(CURRENT_DIRECTORY + "/paldak/upload");

        String uploadFolder = uploadFolderPath.toString();
       
        return uploadFolder;
    }
}