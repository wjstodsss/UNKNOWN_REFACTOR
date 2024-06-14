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
import com.unknown.paldak.admin.domain.FAQVO;
import com.unknown.paldak.admin.service.BaseServiceWithFile;
import com.unknown.paldak.admin.util.AdminCommonAttributes;
import com.unknown.paldak.admin.util.AdminFileUploadManager;
import com.unknown.paldak.admin.util.AdminSortOrder;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("admin/faq/*")
@RequiredArgsConstructor
public class AdminFaqController {

	private final BaseServiceWithFile<FAQVO> faqService;
    private final AdminCommonAttributes addAttribute;
	
	@GetMapping("/list")
	public String list(
		Criteria cri, 
		Model model) {
		
		List<FAQVO> list = faqService.getList(cri);
		model.addAttribute("faqs", list);
        model.addAttribute("pageMaker", faqService.getPageMaker(cri));
        return "admin/faq";
	}
	

	@PostMapping("/register")
	public String register(
		@RequestParam("uploadFile") MultipartFile[] uploadFile,
		FAQVO faqVO,
		@ModelAttribute("cri") Criteria cri,
		RedirectAttributes rttr) {

        cri.setSortType(AdminSortOrder.DESC.getValue());
        cri.setPageNum(AdminSortOrder.FIRST_PAGE_NUM);
        
	    faqService.register(uploadFile, faqVO);
	    
	    addAttribute.addCommonAttributes(rttr, cri);
	    return "redirect:list";
	}

	@GetMapping(value = "/get/{faqId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<FAQVO> get(@PathVariable("faqId") Long faqId) {
		log.info(faqId.toString());
		return new ResponseEntity<>(faqService.get(faqId), HttpStatus.OK);
	}


	
	@PostMapping("/modify")
	public String modify(
		@RequestParam("uploadFile") MultipartFile[] uploadFile,
		FAQVO faqVO,
		@ModelAttribute("cri") Criteria cri,
		RedirectAttributes rttr) {
		
		boolean result = faqService.modify(uploadFile,faqVO);
		if (!result) {
			rttr.addFlashAttribute("error", "FAQ 수정에 실패했습니다.");
	    }
		addAttribute.addCommonAttributes(rttr, cri);
		return "redirect:list";
	}
	
	@PostMapping("/remove")
	public String remove(
		@RequestParam("faqId") Long faqId,
		@ModelAttribute("cri") Criteria cri,
		RedirectAttributes rttr) {
		
		boolean result = faqService.remove(faqId);
		if (!result) {
			rttr.addFlashAttribute("error", "FAQ 삭제에 실패했습니다.");
	    } 
		addAttribute.addCommonAttributes(rttr, cri);
		return "redirect:list";
	}
}