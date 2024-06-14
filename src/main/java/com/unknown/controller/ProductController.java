package com.unknown.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.unknown.mapper.AttachMapper;
import com.unknown.model.BrandVO;
import com.unknown.model.Criteria;
import com.unknown.model.ItemVO;
import com.unknown.service.BrandService;
import com.unknown.service.ItemService;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ItemService itemService;
    
    @Autowired
   private BrandService brandService;
   
    @Autowired
	private AttachMapper attachMapper;


    @RequestMapping(value = "/newArr", method = RequestMethod.GET)
    public String getNewArrProducts(Model model) {
        List<ItemVO> items = itemService.getItemsByCateCode("1000");
        model.addAttribute("items", items);
        return "product/newArr";  // JSP 파일 경로
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getListProducts(@RequestParam(value = "cateCode", required = false) String cateCode,
                                  @RequestParam(value = "cateRange", required = false) String cateRange,
                                  Model model) {
        List<ItemVO> items;
        if (cateRange != null) {
            String[] range = cateRange.split("-");
            items = itemService.getItemsByCateRange(range[0], range[1]);
        } else if (cateCode != null) {
            items = itemService.getItemsByCateCode(cateCode);
        } else {
            items = itemService.getItemsByCateCode("");
        }
        model.addAttribute("items", items);
        return "product/list";  // JSP 파일 경로
    }

    @GetMapping("/display")
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

    @RequestMapping(value = "/promotion", method = RequestMethod.GET)
    public String getPromotions(Model model) {
        List<ItemVO> items = itemService.getAllItems();
        model.addAttribute("items", items);
        return "product/promotion";
    }
    
    @GetMapping("/brand")
    public String getBrandList(Model model) {
        try {
            List<BrandVO> brandList = brandService.brandGetList(new Criteria(1, 100)); // Adjust Criteria as needed
            model.addAttribute("brandList", brandList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "product/brand";
    }
    
    @GetMapping("/timesale")
    @RequestMapping(value = "/timesale", method = RequestMethod.GET)
    public String timeSalePageGET(Model model) {
    	System.out.println("타임 세일 페이지 진입");

        // itemId 1부터 8까지의 상품 가져오기
        List<Integer> itemIds = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        List<ItemVO> timeSaleItems = new LinkedList<>();

        for (Integer itemId : itemIds) {
            ItemVO item = itemService.getGoodsInfo(itemId);
            if (item != null) {
                item.setImageList(attachMapper.getAttachList(itemId)); // 이미지 리스트 추가
                timeSaleItems.add(item);
            }
        }

        model.addAttribute("timeSaleItems", timeSaleItems);

        return "product/timesale";
    }
}
