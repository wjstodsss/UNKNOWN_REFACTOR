package com.unknown.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unknown.model.Criteria;
import com.unknown.model.FAQCategoryVO;
import com.unknown.model.FAQVO;
import com.unknown.model.PageDTO;
import com.unknown.service.FAQCategoryService;
import com.unknown.service.FAQService;
import com.unknown.util.FileUploadManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/faq/*")
@RequiredArgsConstructor
public class FAQController {
	private final FAQService faqService;
	private final FileUploadManager fileUploadManager;
	private final FAQCategoryService faqCategoryService;


	@GetMapping("/faq")
	public void faq(Criteria cri, Model model) {

		log.info("list...............");
		List<FAQVO> list = faqService.getList(cri);

		list.forEach(faq -> log.info(faq));
		model.addAttribute("list", list);
		int total = faqService.getTotal(cri);
		model.addAttribute("pageMaker", new PageDTO(cri, total));
	}

	@GetMapping("/register")
	public void register(Model model) {
		List<FAQCategoryVO> category = faqCategoryService.getAllCategory();
		model.addAttribute("category",category);
	}

	@PostMapping("/register")
		public String register(@RequestParam("uploadFile") MultipartFile[] uploadFile, Model model, FAQVO faqVO, RedirectAttributes rttr) {
		
		if (!uploadFile[0].isEmpty()) { 
			String imageURL = fileUploadManager.uploadFiles(uploadFile);
			faqVO.setFaqImageURL(imageURL);
		}
	    
	    faqService.register(faqVO);
	    
	    rttr.addFlashAttribute("result", faqVO.getFaqId());
	    
	    return "redirect:faq";
	}

	@PostMapping("/modify")
	public String modify(@RequestParam("uploadFile")  MultipartFile[] uploadFile, FAQVO faqVO, @ModelAttribute("cri") Criteria cri,
			RedirectAttributes rttr) {
		if (!uploadFile[0].isEmpty()) { 
			String imageURL = fileUploadManager.uploadFiles(uploadFile);
		    faqVO.setFaqImageURL(imageURL);
		}
		
		log.info("modify:" + faqVO);
		if (faqService.modify(faqVO)) {
			rttr.addFlashAttribute("result", "success");
		}
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		return "redirect:/faq/faq";

	}

	@GetMapping({ "/get", "/modify" })
	public void get(@RequestParam("faqId") Long faqId, @ModelAttribute("cri") Criteria cri, Model model) {
		 FAQVO faq = faqService.get(faqId);
		    model.addAttribute("faq", faq);
		    
		    if (faq.getFaqCategory() != null) {
		        FAQCategoryVO category = faqService.getCategoryById(faq.getFaqCategory());
		        model.addAttribute("categoryValue", category.getCategoryValue());
		    }
		    
		List<FAQCategoryVO> category = faqCategoryService.getAllCategory();
		model.addAttribute("category",category);
		model.addAttribute("selectedCategory", faq.getFaqCategory());
	}

	@PostMapping("/remove")
	public String remove(@RequestParam("faqId") Long faqId, @ModelAttribute("cri") Criteria cri,
			RedirectAttributes rttr) {
		log.info("remove...." + faqId);
		if (faqService.remove(faqId)) {
			rttr.addFlashAttribute("result", "success");
		}
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		return "redirect:/faq/faq";
	}

}
