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
import com.unknown.paldak.admin.common.domain.PageDTO;
import com.unknown.paldak.admin.domain.MemberVO;
import com.unknown.paldak.admin.domain.OrderVO;
import com.unknown.paldak.admin.service.BaseService;
import com.unknown.paldak.admin.service.AdminMemberServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin/member/*")
@RequiredArgsConstructor
public class AdminMemberController {

	private final BaseService<MemberVO> memberService;
	private final AdminMemberServiceImpl memberServiceUtil;

	
	@GetMapping("/list")
	public String list(Criteria cri, Model model) {
		List<MemberVO> list = memberService.getList(cri);
		list.forEach(memberVO -> System.out.println(memberVO));
		model.addAttribute("members", list);
        int total = memberService.getTotal(cri);
        PageDTO pageDTO = new PageDTO(cri, total);
        System.out.println(pageDTO);
        model.addAttribute("pageMaker", pageDTO);
        return "admin/member";
	}

	
	@GetMapping("/descList")
	public String descList(Criteria cri, Model model) {
		List<MemberVO> list = memberService.getDescList(cri);
		list.forEach(memberVO -> System.out.println(memberVO));
		model.addAttribute("members", list);
        int total = memberService.getTotal(cri);
        model.addAttribute("pageMaker", new PageDTO(cri, total));
        return "admin/member";
	}
	
	@PostMapping("/register")
	public String register(Model model, MemberVO memberVO, RedirectAttributes rttr) {
	    memberService.register(memberVO);
	    rttr.addFlashAttribute("result", memberVO.getMemberId());
	    return "redirect:descList";
	}

	@GetMapping(value = "/get/{memberId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<MemberVO> get(@PathVariable("memberId") String memberId) {
		MemberVO memberVO = memberServiceUtil.getByStringId(memberId);
		return new ResponseEntity<>(memberVO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/handleWithdrawal/{memberId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> modifyOrderState(@PathVariable("memberId") String memberId) {
	    MemberVO memberVO = new MemberVO();
	    memberVO.setMemberId(memberId);
	    memberVO.setWithdrawal("Y");
	    
	    boolean result = memberServiceUtil.modifyWithdrawal(memberVO);
	    
	    Map<String, String> response = new HashMap<>();
	    if (result) {
	        response.put("status", "success");
	        return ResponseEntity.ok(response);
	    } else {
	        response.put("status", "fail");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}

	
	@PostMapping("/modify")
	public String modify(MemberVO memberVO, @ModelAttribute("cri") Criteria cri, @RequestParam("currentPath") String currentPath, RedirectAttributes rttr) {
	    if (memberService.modify(memberVO)) {
	        rttr.addFlashAttribute("result", "success");
	    }
	    rttr.addAttribute("pageNum", cri.getPageNum());
	    rttr.addAttribute("amount", cri.getAmount());
	    return "redirect:" + currentPath;
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("memberId") String memberId, @ModelAttribute("cri") Criteria cri, @RequestParam("currentPath") String currentPath, RedirectAttributes rttr) {
		if (memberServiceUtil.removeByStringId(memberId)) {
			rttr.addFlashAttribute("result", "success");
		}
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		System.out.println("remove..." + memberId);
		return "redirect:" + currentPath;
	}
	
	@GetMapping(value = "/checkId/{memberId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, Boolean>> checkDuplicateId(@PathVariable("memberId") String memberId) {
		boolean result = false;
		MemberVO memberVO = memberServiceUtil.getByStringId(memberId);
		
        if(memberVO == null) {
        	result =true;
        }
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("result", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}