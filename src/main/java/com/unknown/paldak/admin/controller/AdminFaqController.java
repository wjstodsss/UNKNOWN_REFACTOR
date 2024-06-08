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
import com.unknown.paldak.admin.domain.FAQVO;
import com.unknown.paldak.admin.service.BaseService;
import com.unknown.paldak.admin.util.AdminFileUploadManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("admin/faq/*")
@RequiredArgsConstructor
public class AdminFaqController {

	private final BaseService<FAQVO> faqService;
    private final AdminFileUploadManager fileUploadManager;


	
	
	@GetMapping("/list")
	public String list(Criteria cri, Model model) {
		System.out.println("jlkjlkjl");
		System.out.println(cri.getPageNum());
		
		List<FAQVO> list = faqService.getList(cri);
		list.forEach(faqVO -> System.out.println(faqVO + "kkkkkkkkkkkkkkkk"));
		model.addAttribute("faqs", list);
		
		//model.addAttribute("pageMaker", new PageDTO(cri, 123)); // ??�??? ??체갯??, 13page
        int total = faqService.getTotal(cri);
        
        model.addAttribute("pageMaker", new PageDTO(cri, total));
        return "admin/faq";
	}

	
	@GetMapping("/descList")
	public String descList(Criteria cri, Model model) {
		System.out.println("1");
		System.out.println(cri);
	
		List<FAQVO> list = faqService.getDescList(cri);
		list.forEach(faqVO -> System.out.println(faqVO + "zzzzzzzzzzzzzzzz"));
		list.forEach(faqVO -> System.out.println(faqVO));
		model.addAttribute("faqs", list);
		
		//model.addAttribute("pageMaker", new PageDTO(cri, 123)); // ??�??? ??체갯??, 13page
        int total = faqService.getTotal(cri);
        
        model.addAttribute("pageMaker", new PageDTO(cri, total));
        return "admin/faq";
	}
	

	@PostMapping("/register")
	public String register(@RequestParam("uploadFile") MultipartFile[] uploadFile, Model model, FAQVO faqVO, RedirectAttributes rttr) {
        System.out.println("kkkk");
        if (!uploadFile[0].isEmpty()) { 
			String imageURL = fileUploadManager.uploadFiles(uploadFile).get("imageURLs");
			faqVO.setFaqImageURL(imageURL);
		}
        
	    faqService.register(faqVO);
	    System.out.println("kkkfsdfsdfsfsdfk");
	    rttr.addFlashAttribute("result", faqVO.getFaqId());
	    
	    return "redirect:descList";
	}

	@GetMapping(value = "/get/{faqId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<FAQVO> get(@PathVariable("faqId") Long faqId) {
		return new ResponseEntity<>(faqService.get(faqId), HttpStatus.OK);
	}


	
	@PostMapping("/modify")
	public String modify(MultipartFile[] uploadFile, FAQVO faqVO, @ModelAttribute("cri") Criteria cri, @RequestParam("currentPath") String currentPath, RedirectAttributes rttr) {
        if (!uploadFile[0].isEmpty()) { 
			String imageURL = fileUploadManager.uploadFiles(uploadFile).get("imageURLs");
			faqVO.setFaqImageURL(imageURL);
		}

	    if (faqService.modify(faqVO)) {
	        rttr.addFlashAttribute("result", "success");
	    }
	    rttr.addAttribute("pageNum", cri.getPageNum());
	    rttr.addAttribute("amount", cri.getAmount());
	    return "redirect:" + currentPath;
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("faqId") Long faqId, @ModelAttribute("cri") Criteria cri, @RequestParam("currentPath") String currentPath, RedirectAttributes rttr) {
		System.out.println("remove..." + faqId);
		if (faqService.remove(faqId)) {
			rttr.addFlashAttribute("result", "success");
		}
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		System.out.println("remove..." + faqId);
		return "redirect:" + currentPath;
	}
}