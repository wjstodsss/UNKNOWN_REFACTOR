package com.unknown.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.unknown.service.OrderService;
import com.unknown.model.ItemSalesDTO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class RankingController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/ranking")
    public String rankingPage(Model model) {
        List<ItemSalesDTO> topSellingItems = orderService.getTopSellingItems();
        model.addAttribute("topSellingItems", topSellingItems);
        return "ranking";
    }

    @GetMapping("/displayRankingImage")
    public ResponseEntity<Resource> displayImage(@RequestParam("fileName") String fileName) throws IOException {
        String uploadFolder = "C:\\upload\\";
        Path filePath = Paths.get(uploadFolder + fileName);

        // 로그 추가
        System.out.println("Requested file path: " + filePath.toString());

        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            System.out.println("File not found or not readable: " + filePath.toString());
            return ResponseEntity.notFound().build();
        }

        String contentType = Files.probeContentType(filePath);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource);
    }
}
