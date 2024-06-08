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
import com.unknown.paldak.admin.service.BaseService;
import com.unknown.paldak.admin.service.AdminReviewReplyServiceImpl;
import com.unknown.paldak.admin.util.AdminFileUploadManager;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("admin/review/*")
@RequiredArgsConstructor
public class AdminReviewController {

	private final BaseService<ReviewVO> reviewService;
	private final AdminReviewReplyServiceImpl replyService;
    private final AdminFileUploadManager fileUploadManager;


	
	
	@GetMapping("/list")
	public String list(Criteria cri, Model model) {
		List<ReviewVO> list = reviewService.getList(cri);
		list.forEach(reviewVO -> System.out.println(reviewVO + "kkkkkkkkkkkkkkkk"));
		model.addAttribute("reviews", list);
        int total = reviewService.getTotal(cri);
        model.addAttribute("pageMaker", new PageDTO(cri, total));
        return "admin/review";
	}

	
	@GetMapping("/descList")
	public String descList(Criteria cri, Model model) {
		List<ReviewReplyVO> replyList = replyService.getList(cri);
		model.addAttribute("replys", replyList);
		replyList.forEach(replyVO -> System.out.println(replyVO + "z------------zz"));
		List<ReviewVO> list = reviewService.getDescList(cri);
		list.forEach(reviewVO -> System.out.println(reviewVO + "zzzzzzzzzzzzzzzz"));
		list.forEach(reviewVO -> System.out.println(reviewVO));
		model.addAttribute("reviews", list);
        int total = reviewService.getTotal(cri);
        model.addAttribute("pageMaker", new PageDTO(cri, total));
        return "admin/review";
	}
	

	@PostMapping("/register")
	public String register(@RequestParam("uploadFile") MultipartFile[] uploadFile, Model model, ReviewVO reviewVO, RedirectAttributes rttr) {
        System.out.println("kkkk");
        if (!uploadFile[0].isEmpty()) { 
			String imageURL = fileUploadManager.uploadFiles(uploadFile).get("imageURLs");
			reviewVO.setReviewImageURL(imageURL);
		}


	    reviewService.register(reviewVO);
	    System.out.println("kkkfsdfsdfsfsdfk");
	    rttr.addFlashAttribute("result", reviewVO.getReviewId());
	    
	    return "redirect:descList";
	}

	@GetMapping(value = "/get/{reviewId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String, Object>> get(@PathVariable("reviewId") Long reviewId) {
	    ReviewVO review = reviewService.get(reviewId);
	    System.out.println(review);
	    ReviewReplyVO reply = replyService.getByReviewId(reviewId); 
	    System.out.println(reply);
	    Map<String, Object> responseData = new HashMap<>();
	    responseData.put("review", review);
	    responseData.put("reply", reply);

	    // ResponseEntity?? JSON ?°ì?´í?? ë°???
	    return new ResponseEntity<>(responseData, HttpStatus.OK);
	}


	
	@PostMapping("/modify")
	public String modify(MultipartFile[] uploadFile, ReviewVO reviewVO, @ModelAttribute("cri") Criteria cri, ReviewReplyVO replyVO, @RequestParam("currentPath") String currentPath, RedirectAttributes rttr) {
        if (!uploadFile[0].isEmpty()) { 
			String imageURL = fileUploadManager.uploadFiles(uploadFile).get("imageURLs");
			reviewVO.setReviewImageURL(imageURL);
		}

        if(replyVO.getReplyId() == null){
        	System.out.println("------------------------");
        	replyService.register(replyVO);
        }
        
        
        replyService.modify(replyVO);
        
	    if (reviewService.modify(reviewVO)) {
	        rttr.addFlashAttribute("result", "success");
	    }
	    
	    rttr.addAttribute("pageNum", cri.getPageNum());
	    rttr.addAttribute("amount", cri.getAmount());
	    return "redirect:" + currentPath;
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("reviewId") Long reviewId, @ModelAttribute("cri") Criteria cri, @RequestParam("currentPath") String currentPath, RedirectAttributes rttr) {
		
		System.out.println("remove..." + reviewId);
		if (reviewService.remove(reviewId)) {
			rttr.addFlashAttribute("result", "success");
		}
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		System.out.println("remove..." + reviewId);
		return "redirect:" + currentPath;
	}
}