package com.unknown.paldak.admin.controller;

import java.util.List;

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
import com.unknown.paldak.admin.domain.NoticeVO;
import com.unknown.paldak.admin.service.BaseService;

import com.unknown.paldak.admin.util.AdminFileUploadManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("admin/notice/*")
@RequiredArgsConstructor
public class AdminNoticeController {

	private final BaseService<NoticeVO> noticeService;
    private final AdminFileUploadManager fileUploadManager;


	
	
	@GetMapping("/list")
	public String list(Criteria cri, Model model) {
		System.out.println("jlkjlkjl");
		System.out.println(cri.getPageNum());
		
		List<NoticeVO> list = noticeService.getList(cri);
		list.forEach(noticeVO -> System.out.println(noticeVO + "kkkkkkkkkkkkkkkk"));
		model.addAttribute("notices", list);
		
		//model.addAttribute("pageMaker", new PageDTO(cri, 123)); // ??�??? ??체갯??, 13page
        int total = noticeService.getTotal(cri);
        
        model.addAttribute("pageMaker", new PageDTO(cri, total));
        return "admin/notice";
	}

	
	@GetMapping("/descList")
	public String descList(Criteria cri, Model model) {
		System.out.println("1");
		System.out.println(cri);
	
		List<NoticeVO> list = noticeService.getDescList(cri);
		list.forEach(noticeVO -> System.out.println(noticeVO + "zzzzzzzzzzzzzzzz"));
		list.forEach(noticeVO -> System.out.println(noticeVO));
		model.addAttribute("notices", list);
		
		//model.addAttribute("pageMaker", new PageDTO(cri, 123)); // ??�??? ??체갯??, 13page
        int total = noticeService.getTotal(cri);
        
        model.addAttribute("pageMaker", new PageDTO(cri, total));
        return "admin/notice";
	}
	

	@PostMapping("/register")
	public String register(@RequestParam("uploadFile") MultipartFile[] uploadFile, Model model, NoticeVO noticeVO, RedirectAttributes rttr) {
        System.out.println("kkkk");
        if (!uploadFile[0].isEmpty()) { 
			String imageURL = fileUploadManager.uploadFiles(uploadFile).get("imageURLs");
			noticeVO.setNoticeImageURL(imageURL);
		}


	    noticeService.register(noticeVO);
	    System.out.println("kkkfsdfsdfsfsdfk");
	    rttr.addFlashAttribute("result", noticeVO.getNoticeId());
	    
	    return "redirect:descList";
	}

	@GetMapping(value = "/get/{noticeId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<NoticeVO> get(@PathVariable("noticeId") Long noticeId) {
		return new ResponseEntity<>(noticeService.get(noticeId), HttpStatus.OK);
	}


	
	@PostMapping("/modify")
	public String modify(MultipartFile[] uploadFile, NoticeVO noticeVO, @ModelAttribute("cri") Criteria cri, @RequestParam("currentPath") String currentPath, RedirectAttributes rttr) {
        if (!uploadFile[0].isEmpty()) { 
			String imageURL = fileUploadManager.uploadFiles(uploadFile).get("imageURLs");
			noticeVO.setNoticeImageURL(imageURL);
		}

	    if (noticeService.modify(noticeVO)) {
	        rttr.addFlashAttribute("result", "success");
	    }
	    rttr.addAttribute("pageNum", cri.getPageNum());
	    rttr.addAttribute("amount", cri.getAmount());
	    return "redirect:" + currentPath;
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("noticeId") Long noticeId, @ModelAttribute("cri") Criteria cri, @RequestParam("currentPath") String currentPath, RedirectAttributes rttr) {
		System.out.println("remove..." + noticeId);
		if (noticeService.remove(noticeId)) {
			rttr.addFlashAttribute("result", "success");
		}
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		System.out.println("remove..." + noticeId);
		return "redirect:" + currentPath;
	}
}