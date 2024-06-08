package com.unknown.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unknown.model.BenefitsReplyVO;
import com.unknown.model.BenefitsVO;
import com.unknown.model.Criteria;
import com.unknown.model.MemberVO;
import com.unknown.model.PageDTO;
import com.unknown.service.BenefitsReplyService;
import com.unknown.service.BenefitsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/benefits")
@RequiredArgsConstructor
public class BenefitsController {
	private final BenefitsService benefitsService;
	private final BenefitsReplyService benefitsReplyService;

	@GetMapping("")
	public void benefits(Criteria cri, Model model) {
		log.info("list...............");
		List<BenefitsVO> list = benefitsService.getList(cri);
		List<BenefitsReplyVO> replyList = benefitsReplyService.getList();
		model.addAttribute("list", list);
		model.addAttribute("replyList", replyList);
		int total = benefitsService.getTotal(cri);
		int replyTotal = benefitsReplyService.getTotal(cri);
		model.addAttribute("pageMaker", new PageDTO(cri, total));
		model.addAttribute("replyPageMaker", new PageDTO(cri, replyTotal));
	}

	@GetMapping("/register")
	public void register() {
		// 등록 페이지를 보여줌
	}

	@PostMapping("/register")
	public String register(BenefitsVO benefitsVO, HttpSession session, RedirectAttributes rttr) {
	    MemberVO currentMember = (MemberVO) session.getAttribute("member");
	    if (currentMember == null) {
	        throw new IllegalStateException("No authenticated user found in session");
	    }

	    String currentUserName = currentMember.getMemberId();
	    log.info("Current user: " + currentUserName);

	    if (benefitsVO == null) {
	        log.error("BenefitsVO object is null");
	        throw new IllegalArgumentException("BenefitsVO object cannot be null");
	    }

	    benefitsVO.setBenefitsWriter(currentUserName);
	    benefitsService.register(benefitsVO);
	    rttr.addFlashAttribute("result", benefitsVO.getBenefitsId());
	    return "redirect:/benefits";
	}

	@PostMapping("/modify")
	public String modify(BenefitsVO benefitsVO, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("modify:" + benefitsVO);
		if (benefitsService.modify(benefitsVO)) {
			rttr.addFlashAttribute("result", "success");
		}
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		return "redirect:/benefits";
	}

	@GetMapping({ "/get", "/modify" })
	public void get(@RequestParam("benefitsId") Long benefitsId, @ModelAttribute("cri") Criteria cri, Model model) {
		model.addAttribute("benefits", benefitsService.get(benefitsId));
	}

	@PostMapping("/remove")
	public String remove(@RequestParam("benefitsId") Long benefitsId, @ModelAttribute("cri") Criteria cri,
			RedirectAttributes rttr) {
		log.info("remove...." + benefitsId);
		if (benefitsService.remove(benefitsId)) {
			rttr.addFlashAttribute("result", "success");
		}
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		return "redirect:/benefits";
	}
}
