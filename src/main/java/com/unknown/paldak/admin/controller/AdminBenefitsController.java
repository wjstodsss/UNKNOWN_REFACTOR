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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.domain.BenefitsReplyVO;
import com.unknown.paldak.admin.domain.BenefitsVO;
import com.unknown.paldak.admin.service.AdminBenefitsReplyServiceImpl;
import com.unknown.paldak.admin.service.AdminBenefitsServiceImpl;
import com.unknown.paldak.admin.service.BaseService;
import com.unknown.paldak.admin.util.AdminCommonAttributes;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin/benefits/*")
@RequiredArgsConstructor
public class AdminBenefitsController {

	private final BaseService<BenefitsVO> benefitsService;
	private final AdminBenefitsReplyServiceImpl replyService;
	private final AdminBenefitsServiceImpl benefitsServiceUtil;
	private final AdminCommonAttributes addAttribute;

	@GetMapping("/list")
	public String list(
		Criteria cri,
		Model model) {
		
		List<BenefitsVO> list = benefitsServiceUtil.getBenefitsList(cri);
		
		model.addAttribute("benefits", list);
		model.addAttribute("pageMaker", benefitsService.getPageMaker(cri));
		return "admin/benefits";
	}

	@PostMapping("/register")
	public String register(
		@RequestParam("sortType") String sortType,
		Criteria cri, 
		Model model, 
		BenefitsVO benefitsVO,
		RedirectAttributes rttr) {
		
		benefitsService.register(benefitsVO);
		rttr.addFlashAttribute("result", benefitsVO.getBenefitsId());
		addAttribute.addCommonAttributes(rttr, cri, sortType);
		return "redirect:list";
	}

	@GetMapping(value = "/get/{benefitsId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String, Object>> get(
		@PathVariable("benefitsId") Long benefitsId) {
		
		BenefitsVO benefits = benefitsService.get(benefitsId);
		BenefitsReplyVO reply = replyService.getByBenefitsId(benefitsId);
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("benefits", benefits);
		responseData.put("reply", reply);
		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@PostMapping("/modify")
	public String modify(
		@RequestParam("sortType") String sortType, 
		@ModelAttribute("cri") Criteria cri,
		BenefitsVO benefitsVO,	
		BenefitsReplyVO replyVO, 
		RedirectAttributes rttr) {
		
		if (replyVO.getBenefitsReplyId() == null) {
			replyService.register(replyVO);
		} else {
			replyService.modify(replyVO);
		}
		

		if (benefitsService.modify(benefitsVO)) {
			rttr.addFlashAttribute("result", "success");
		}

		addAttribute.addCommonAttributes(rttr, cri, sortType);
		return "redirect:list";
	}

	@PostMapping("/remove")
	public String remove(
		@RequestParam("benefitsId") Long benefitsId,
		@RequestParam("sortType") String sortType, 
		@ModelAttribute("cri") Criteria cri,
		RedirectAttributes rttr){
	
		replyService.remove(benefitsId);
		
		if (benefitsService.remove(benefitsId)) {
			rttr.addFlashAttribute("result", "success");
		}
		
		addAttribute.addCommonAttributes(rttr, cri, sortType);
		return "redirect:list";
	}
}