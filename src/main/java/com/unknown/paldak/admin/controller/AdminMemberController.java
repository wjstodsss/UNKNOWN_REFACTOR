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
import com.unknown.paldak.admin.domain.MemberVO;
import com.unknown.paldak.admin.service.AdminMemberServiceImpl;
import com.unknown.paldak.admin.service.BaseServiceDefault;
import com.unknown.paldak.admin.util.AdminCommonAttributes;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin/member/*")
@RequiredArgsConstructor
public class AdminMemberController {

	private final BaseServiceDefault<MemberVO> memberService;
	private final AdminMemberServiceImpl memberServiceUtil;
	private final AdminCommonAttributes addAttribute;

	
	@GetMapping("/list")
	public String list(
		Criteria cri, 
		Model model) {
		
		List<MemberVO> list = memberService.getList(cri);
		model.addAttribute("members", list);
        model.addAttribute("pageMaker", memberService.getPageMaker(cri));
        return "admin/member";
	}
	
	@PostMapping("/register")
	public String register(
		MemberVO memberVO,
		@ModelAttribute("cri") Criteria cri,
		RedirectAttributes rttr) {
		
	    memberService.register(memberVO);
	    addAttribute.addCommonAttributes(rttr, cri);
	    return "redirect:list";
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
	public String modify(
		MemberVO memberVO,
		@ModelAttribute("cri") Criteria cri,
		RedirectAttributes rttr) {
		
		boolean result = memberService.modify(memberVO);
	    if (!result) {
	        rttr.addFlashAttribute("error", "회원 정보 수정에 실패했습니다.");
	    }
	    addAttribute.addCommonAttributes(rttr, cri);
		return "redirect:list";
	}
	
	@PostMapping("/remove")
	public String remove(
		@RequestParam("memberId") String memberId,
		@ModelAttribute("cri") Criteria cri,
		RedirectAttributes rttr) {
		
		
		boolean result = memberServiceUtil.removeByStringId(memberId);
	    if (!result) {
	        rttr.addFlashAttribute("error", "회원 정보 삭제에 실패했습니다.");
	    }
	    addAttribute.addCommonAttributes(rttr, cri);
		return "redirect:list";
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