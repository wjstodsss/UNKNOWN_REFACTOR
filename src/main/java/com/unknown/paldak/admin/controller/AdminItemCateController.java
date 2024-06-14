package com.unknown.paldak.admin.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.common.domain.PageDTO;
import com.unknown.paldak.admin.domain.ItemCateVO;
import com.unknown.paldak.admin.domain.MemberVO;
import com.unknown.paldak.admin.service.AdminItemCateServiceImpl;
import com.unknown.paldak.admin.service.BaseServiceDefault;
import com.unknown.paldak.admin.util.AdminCommonAttributes;
import com.unknown.paldak.admin.util.AdminSortOrder;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin/itemCate/*")
@RequiredArgsConstructor
public class AdminItemCateController {

	private final BaseServiceDefault<ItemCateVO> itemCateService;
	private final AdminItemCateServiceImpl itemCateListService;
	private final AdminCommonAttributes addAttribute;
	
	@GetMapping(value = "/select/{page}/{cateCode}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Map<String, Object>> getList(@PathVariable("cateCode") String cateCode, @PathVariable("page") int page) {
	    Map<String, Object> responseData = new HashMap<>();
	    String cateParent = cateCode;
	    try {
	        Criteria cri = new Criteria(page, 10);
	        List<ItemCateVO> list = itemCateListService.getListByCateParent(cri, cateParent);
	       
	        int total = itemCateListService.getTotalByCateParent(cri, cateParent);
	        
	        PageDTO pageMaker = new PageDTO(cri, total);
	        responseData.put("list", list);
	        responseData.put("pageMaker", pageMaker);

	        return ResponseEntity.ok(responseData);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body(Collections.singletonMap("error", "목록을 생성 할 수 없습니다."));
	    }
	}


	@GetMapping("/list")
	public String list(
		Criteria cri, 
		Model model) {
		
		List<ItemCateVO> list = itemCateService.getList(cri);
		model.addAttribute("itemCates", list);
        model.addAttribute("pageMaker", itemCateService.getPageMaker(cri));
        return "admin/itemCate";
	}

	
	@PostMapping("/register")
	public String register(
		ItemCateVO itemCateVO, 
		@ModelAttribute("cri") Criteria cri,
		RedirectAttributes rttr) {
		
	    itemCateService.register(itemCateVO);
	    
	    addAttribute.addCommonAttributes(rttr, cri);
	    return "redirect:list";
	}
	


	@GetMapping(value = "/get/{cateCode}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ItemCateVO> get(@PathVariable("cateCode") String cateCode) {
		ItemCateVO item = itemCateListService.getByStringId(cateCode);
		return new ResponseEntity<>(item, HttpStatus.OK);
	}


	@PostMapping("/modify")
	public String modify(
		ItemCateVO itemCateVO, 
		@ModelAttribute("cri") Criteria cri, 
		RedirectAttributes rttr) {

	    boolean result = itemCateService.modify(itemCateVO);
		if (!result) {
			rttr.addFlashAttribute("error", "카테고리를 수정 할 수 없습니다.");
	    } 
		addAttribute.addCommonAttributes(rttr, cri);
	    return "redirect:list";
	}
	
	@PostMapping("/remove")
	public String remove(
	    @RequestParam("cateCode") String cateCode, 
	    @ModelAttribute("cri") Criteria cri,
	    RedirectAttributes rttr) {

	    
        boolean result = itemCateListService.removeByStringId(cateCode);
        
        if (!result) {
        	rttr.addFlashAttribute("error", "해당 카테고리는 삭제 할 수 없습니다.");
        }
        
	    addAttribute.addCommonAttributes(rttr, cri);
	    return "redirect:list";
	}
	
	@GetMapping(value = "/checkCateCode/{cateCode}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, Boolean>> checkDuplicateId(
		@PathVariable("cateCode") String cateCode) {
		boolean result = false;
		ItemCateVO itemCateVO = itemCateListService.getByStringId(cateCode);
		
        if(itemCateVO == null) {
        	result = true;
        }
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("result", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	


}