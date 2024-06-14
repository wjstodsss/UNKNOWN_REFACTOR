package com.unknown.paldak.admin.controller;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.common.domain.PageDTO;
import com.unknown.paldak.admin.domain.AttachImageVO;
import com.unknown.paldak.admin.domain.ItemCateVO;
import com.unknown.paldak.admin.domain.ItemVO;
import com.unknown.paldak.admin.domain.ReviewReplyVO;
import com.unknown.paldak.admin.service.AdminAttachServiceImpl;
import com.unknown.paldak.admin.service.AdminItemCateServiceImpl;
import com.unknown.paldak.admin.service.AdminItemServiceImpl;
import com.unknown.paldak.admin.service.BaseServiceWithFile;
import com.unknown.paldak.admin.util.AdminCommonAttributes;
import com.unknown.paldak.admin.util.AdminFileUploadManager;
import com.unknown.paldak.admin.util.AdminSortOrder;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("admin/item/*")
@RequiredArgsConstructor
public class AdminItemController {
	private String SOLDOUT = "품절";
	
	private final BaseServiceWithFile<ItemVO> itemService;
    private final AdminCommonAttributes addAttribute;
    private final AdminItemCateServiceImpl itemCateService;
    private final AdminItemServiceImpl itemServiceUtil;
    


	@GetMapping("/list")
	public String list(
		Criteria cri, 
		Model model) {
		
		List<ItemCateVO> cateList = itemCateService.getList();
		List<ItemVO> list = itemService.getList(cri);
		model.addAttribute("items", list);
		model.addAttribute("categorys", cateList);
        model.addAttribute("pageMaker", itemService.getPageMaker(cri));     
        return "admin/item";
	}

	

	@PostMapping("/register")
	public String register(
		@RequestParam("uploadFile") MultipartFile[] uploadFile, 
		@ModelAttribute("cri") Criteria cri,
		ItemVO itemVO, 
		RedirectAttributes rttr) {
		
		cri.setSortType(AdminSortOrder.DESC.getValue());
        cri.setPageNum(AdminSortOrder.FIRST_PAGE_NUM);
        
        itemService.register(uploadFile, itemVO);
        itemServiceUtil.registerItemState(itemVO);
	    addAttribute.addCommonAttributes(rttr, cri);
	    return "redirect:list";
	}

	@GetMapping(value = "/get/{itemId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String, Object>> get(@PathVariable("itemId") long itemId) {
	    ItemVO item = itemService.get(itemId);
	    Map<String, Object> responseData = new HashMap<>();
	    responseData.put("item", item);
	    return new ResponseEntity<>(responseData, HttpStatus.OK);
	}
	
	@GetMapping(value = "/itemState/{itemId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ItemVO> modifyItemState(@PathVariable("itemId") long itemId) {
		ItemVO itemVO = new ItemVO();
		itemVO.setItemId(itemId);
		itemVO.setItemState(SOLDOUT);
	    
	    boolean result = itemServiceUtil.modifyItemState(itemVO);
	    
	    if (result) {
	        return new ResponseEntity<>(itemVO, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@GetMapping(value = "/checkItem/{itemId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, Boolean>> checkDuplicateId(@PathVariable("itemId") long itemId) {
		boolean result = false;
		
		ItemVO itemVO = itemService.get(itemId);
		
        if(itemVO != null) {
        	result = true;
        }
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("result", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@PostMapping("/modify")
	public String modify(
		MultipartFile[] uploadFile,
		ItemVO itemVO, 
		@ModelAttribute("cri") Criteria cri,
		RedirectAttributes rttr) {
		
		boolean result = itemService.modify(uploadFile, itemVO);
	    if (!result) {
	        rttr.addFlashAttribute("error", "상품 수정에 실패했습니다.");
	    }
	    addAttribute.addCommonAttributes(rttr, cri);
		return "redirect:list";
	}
	
//	상품참조관계와 제품이력 관리를 위해 사용하지 않음
//	@PostMapping("/remove")
//	public String remove(@RequestParam("itemId") Long itemId, @ModelAttribute("cri") Criteria cri, @RequestParam("currentPath") String currentPath, RedirectAttributes rttr) {
//		
//		String itemImageURL = itemService.get(itemId).getItemImageURL();
//		
//		boolean result = itemService.remove(itemId);
//	    if (!result) {
//	        rttr.addFlashAttribute("error", "상품 삭제 작업에 실패했습니다.");
//	    }
//		
//		fileUploadManager.deleteFile(itemImageURL);
//		addAttribute.addCommonAttributes(rttr, cri);
//		return "redirect:list";
//	}

}