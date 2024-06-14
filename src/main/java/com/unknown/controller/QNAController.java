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
import com.unknown.model.PageDTO;
import com.unknown.model.QNACategoryVO;
import com.unknown.model.QNAVO;
import com.unknown.service.QNACategoryService;
import com.unknown.service.QNAService;
import com.unknown.util.FileUploadManager;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/qna/*")
@RequiredArgsConstructor
public class QNAController {
	private final QNAService qnaService;
	private final FileUploadManager fileUploadManager;
	private final QNACategoryService qnaCategoryService;
	


	@GetMapping("/qna")
	public void qna(Criteria cri, Model model) {

		log.info("list...............");
		List<QNAVO> list = qnaService.getList(cri);

		list.forEach(qna -> log.info(qna));
		model.addAttribute("list", list);
		int total = qnaService.getTotal(cri);
		model.addAttribute("pageMaker", new PageDTO(cri, total));
		}

	@GetMapping("/register")
	public void register(Model model) {
		List<QNACategoryVO> category = qnaCategoryService.getAllCategory();
		model.addAttribute("category",category);
	}

	@PostMapping("/register")
		public String register(@RequestParam("uploadFile") MultipartFile[] uploadFile, Model model, QNAVO qnaVO, RedirectAttributes rttr) {
		
		if (!uploadFile[0].isEmpty()) { 
			String imageURL = fileUploadManager.uploadFiles(uploadFile);
			qnaVO.setQnaImageURL(imageURL);
		}
	    
	    qnaService.register(qnaVO);
	    
	    rttr.addFlashAttribute("result", qnaVO.getQnaId());
	    
	    return "redirect:qna";
	}

	@PostMapping("/modify")
	public String modify(@RequestParam("uploadFile")  MultipartFile[] uploadFile, QNAVO qnaVO, @ModelAttribute("cri") Criteria cri,
			RedirectAttributes rttr) {
		if (!uploadFile[0].isEmpty()) { 
			String imageURL = fileUploadManager.uploadFiles(uploadFile);
		    qnaVO.setQnaImageURL(imageURL);
		}
		
		log.info("modify:" + qnaVO);
		if (qnaService.modify(qnaVO)) {
			rttr.addFlashAttribute("result", "success");
		}
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		return "redirect:/qna/qna";

	}

	@GetMapping({ "/get", "/modify" })
	public void get(@RequestParam("qnaId") Long qnaId, @ModelAttribute("cri") Criteria cri, Model model) {
		 QNAVO qna = qnaService.get(qnaId);
		    model.addAttribute("qna", qna);
		    
		    if (qna.getQnaCategory() != null) {
		        QNACategoryVO category = qnaService.getCategoryById(qna.getQnaCategory());
		        model.addAttribute("categoryValue", category.getCategoryValue());
		    }
		    
		List<QNACategoryVO> category = qnaCategoryService.getAllCategory();
		model.addAttribute("category",category);
		model.addAttribute("selectedCategory", qna.getQnaCategory());
	}

	@PostMapping("/remove")
	public String remove(@RequestParam("qnaId") Long qnaId, @ModelAttribute("cri") Criteria cri,
			RedirectAttributes rttr) {
		log.info("ffffffffffffffffffffffffffffffffffffff"+qnaId);
		log.info("remove...." + qnaId);
		if (qnaService.remove(qnaId)) {
			rttr.addFlashAttribute("result", "success");
		}
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		return "redirect:/qna/qna";
	}

}
