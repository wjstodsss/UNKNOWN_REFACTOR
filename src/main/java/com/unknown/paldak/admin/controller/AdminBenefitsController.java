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
import com.unknown.paldak.admin.domain.BenefitsReplyVO;
import com.unknown.paldak.admin.domain.BenefitsVO;
import com.unknown.paldak.admin.service.BaseService;
import com.unknown.paldak.admin.service.AdminBenefitsReplyServiceImpl;



import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("admin/benefits/*")
@RequiredArgsConstructor
public class AdminBenefitsController {

	private final BaseService<BenefitsVO> benefitsService;
	private final AdminBenefitsReplyServiceImpl replyService;

	
	@GetMapping("/list")
	public String list(Criteria cri, Model model) {
		System.out.println("kjjkhkh");
		List<BenefitsVO> list = benefitsService.getList(cri);
		model.addAttribute("benefits", list);
		int total = benefitsService.getTotal(cri);
        model.addAttribute("pageMaker", new PageDTO(cri, total));
        return "admin/benefits";
	}

	
	@GetMapping("/descList")
	public String descList(Criteria cri, Model model) {
		List<BenefitsVO> list = benefitsService.getDescList(cri);
		list.forEach(benefitsVO -> System.out.println(benefitsVO));
		model.addAttribute("benefits", list);
        int total = benefitsService.getTotal(cri);
        System.out.println(total);
        model.addAttribute("pageMaker", new PageDTO(cri, total));
        return "admin/benefits";
	}
	

	@PostMapping("/register")
	public String register(Model model, BenefitsVO benefitsVO, RedirectAttributes rttr) {
	    
	    
	    benefitsService.register(benefitsVO);
	    System.out.println("kkkfsdfsdfsfsdfk");
	    rttr.addFlashAttribute("result", benefitsVO.getBenefitsId());
	    
	    return "redirect:descList";
	}

	@GetMapping(value = "/get/{benefitsId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String, Object>> get(@PathVariable("benefitsId") Long benefitsId) {
	    BenefitsVO benefits = benefitsService.get(benefitsId);
	    System.out.println(benefits);
	    BenefitsReplyVO reply = replyService.getByBenefitsId(benefitsId); 
	    System.out.println(reply);
	    Map<String, Object> responseData = new HashMap<>();
	    responseData.put("benefits", benefits);
	    responseData.put("reply", reply);

	    // ResponseEntity?? JSON ?°ì?´í?? ë°???
	    return new ResponseEntity<>(responseData, HttpStatus.OK);
	}


	
	@PostMapping("/modify")
	public String modify(BenefitsVO benefitsVO, @ModelAttribute("cri") Criteria cri, BenefitsReplyVO replyVO, @RequestParam("currentPath") String currentPath, RedirectAttributes rttr) {
		System.out.println("kkkfsdfsdfsfsdfk" + replyVO.getBenefitsReplyId());
        if(replyVO.getBenefitsReplyId() == null){
        	System.out.println("------------------------");
        	replyService.register(replyVO);
        }
        replyService.modify(replyVO);
        System.out.println("kkkfsdfsdfsfsdfk-------------");
	    if (benefitsService.modify(benefitsVO)) {
	    	System.out.println("kkkfsdfsd99999999999fsfsdfk" + benefitsService.modify(benefitsVO));
	        rttr.addFlashAttribute("result", "success");
	    }
	    
	    rttr.addAttribute("pageNum", cri.getPageNum());
	    rttr.addAttribute("amount", cri.getAmount());
	    return "redirect:" + currentPath;
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("benefitsId") Long benefitsId, @ModelAttribute("cri") Criteria cri, @RequestParam("currentPath") String currentPath, RedirectAttributes rttr) {
		replyService.remove(benefitsId);
		System.out.println("remove..." + benefitsId);
		if (benefitsService.remove(benefitsId)) {
			rttr.addFlashAttribute("result", "success");
		}
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		System.out.println("remove..." + benefitsId);
		return "redirect:" + currentPath;
	}
}