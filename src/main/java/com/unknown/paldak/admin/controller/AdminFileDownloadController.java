package com.unknown.paldak.admin.controller;


import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.unknown.paldak.admin.util.AdminUploadPathConfig;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminFileDownloadController {

    private final AdminUploadPathConfig uploadPathConfig;

    @GetMapping("/download/{year}/{month}/{day}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String year,
                                                 @PathVariable String month,
                                                 @PathVariable String day,
                                                 @PathVariable String fileName) {
        String uploadDirectory = uploadPathConfig.getUploadPath();
        String filePathString = String.join(File.separator, uploadDirectory, year, month, day, fileName);
        Path filePath = Paths.get(filePathString).normalize();
        Resource resource;

        try {
            resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }
}