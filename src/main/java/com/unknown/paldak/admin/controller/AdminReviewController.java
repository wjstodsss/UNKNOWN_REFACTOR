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
import com.unknown.paldak.admin.domain.ReviewReplyVO;
import com.unknown.paldak.admin.domain.ReviewVO;
import com.unknown.paldak.admin.service.AdminReviewReplyServiceImpl;
import com.unknown.paldak.admin.service.BaseServiceWithFile;
import com.unknown.paldak.admin.util.AdminCommonAttributes;
import com.unknown.paldak.admin.util.AdminFileUploadManager;
import com.unknown.paldak.admin.util.AdminSortOrder;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("admin/review/*")
@RequiredArgsConstructor
public class AdminReviewController {

	private final BaseServiceWithFile<ReviewVO> reviewService;
	private final AdminReviewReplyServiceImpl replyService;
	private final AdminCommonAttributes addAttribute;


	
	
	@GetMapping("/list")
	public String list(Criteria cri, Model model) {
		
		List<ReviewVO> list = reviewService.getList(cri);
		model.addAttribute("reviews", list);
		model.addAttribute("pageMaker", reviewService.getPageMaker(cri));
        return "admin/review";
	}

	@PostMapping("/register")
	public String register(
		@RequestParam("uploadFile") MultipartFile[] uploadFile,
		ReviewVO reviewVO,
		@ModelAttribute("cri") Criteria cri,
		RedirectAttributes rttr) {

		cri.setSortType(AdminSortOrder.DESC.getValue());
        cri.setPageNum(AdminSortOrder.FIRST_PAGE_NUM);
        
	    reviewService.register(uploadFile, reviewVO);
	    
	    addAttribute.addCommonAttributes(rttr, cri);
	    return "redirect:list";
	}

	@GetMapping(value = "/get/{reviewId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String, Object>> get(@PathVariable("reviewId") Long reviewId) {
	    ReviewVO review = reviewService.get(reviewId);
	    ReviewReplyVO reply = replyService.getByReviewId(reviewId); 
	    Map<String, Object> responseData = new HashMap<>();
	    responseData.put("review", review);
	    responseData.put("reply", reply);
	    return new ResponseEntity<>(responseData, HttpStatus.OK);
	}


	
	@PostMapping("/modify")
	public String modify(
		@RequestParam("uploadFile") MultipartFile[] uploadFile,
		ReviewVO reviewVO, 
		@ModelAttribute("cri") Criteria cri, 
		ReviewReplyVO replyVO, 
		RedirectAttributes rttr) {
        
        if(replyVO.getReplyId() == null){
        	replyService.register(replyVO);
        }
        
        replyService.modify(replyVO);
        
	    boolean result = reviewService.modify(uploadFile,reviewVO);
		if (!result) {
			rttr.addFlashAttribute("error", "REVIEW 수정에 실패했습니다.");
	    }
		addAttribute.addCommonAttributes(rttr, cri);
		return "redirect:list";
	}
	
	@PostMapping("/remove")
	public String remove(
		@RequestParam("reviewId") Long reviewId, 
		@ModelAttribute("cri") Criteria cri, 
		RedirectAttributes rttr) {
		
		boolean result = reviewService.remove(reviewId);
		if (!result) {
			rttr.addFlashAttribute("error", "REVIEW 삭제에 실패했습니다.");
	    } 
		addAttribute.addCommonAttributes(rttr, cri);
		return "redirect:list";
	}
}