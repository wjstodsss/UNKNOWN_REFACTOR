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
import com.unknown.paldak.admin.domain.NoticeVO;
import com.unknown.paldak.admin.service.BaseServiceWithFile;
import com.unknown.paldak.admin.util.AdminCommonAttributes;
import com.unknown.paldak.admin.util.AdminSortOrder;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin/notice/*")
@RequiredArgsConstructor
public class AdminNoticeController {

	private final BaseServiceWithFile<NoticeVO> noticeService;
	private final AdminCommonAttributes addAttribute;
	
	@GetMapping("/list")
	public String list(
		Criteria cri, 
		Model model) {
		
		List<NoticeVO> list = noticeService.getList(cri);
		model.addAttribute("notices", list);
		model.addAttribute("pageMaker", noticeService.getPageMaker(cri));
        return "admin/notice";
	}

	
	@PostMapping("/register")
	public String register(
		@RequestParam("uploadFile") MultipartFile[] uploadFile, 
		NoticeVO noticeVO,
		@ModelAttribute("cri") Criteria cri,
		RedirectAttributes rttr) {
		
		cri.setSortType(AdminSortOrder.DESC.getValue());
        cri.setPageNum(AdminSortOrder.FIRST_PAGE_NUM);

	    noticeService.register(uploadFile, noticeVO);
	    
	    addAttribute.addCommonAttributes(rttr, cri);
	    return "redirect:list";
	}

	@GetMapping(value = "/get/{noticeId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<NoticeVO> get(@PathVariable("noticeId") Long noticeId) {
		return new ResponseEntity<>(noticeService.get(noticeId), HttpStatus.OK);
	}


	
	@PostMapping("/modify")
	public String modify(
		MultipartFile[] uploadFile, 
		NoticeVO noticeVO, 
		@ModelAttribute("cri") Criteria cri, 
		RedirectAttributes rttr) {
	    
	    boolean result = noticeService.modify(uploadFile, noticeVO);
		if (!result) {
			rttr.addFlashAttribute("error", "공지사항 수정에 실패했습니다.");
	    }
		
	    addAttribute.addCommonAttributes(rttr, cri);
		return "redirect:list";
	}
	
	@PostMapping("/remove")
	public String remove(
		@RequestParam("noticeId") Long noticeId, 
		@ModelAttribute("cri") Criteria cri,
		RedirectAttributes rttr) {

		boolean result = noticeService.remove(noticeId);
		if (!result) {
			rttr.addFlashAttribute("error", "게시글 삭제에 실패했습니다.");
	    }
		
		addAttribute.addCommonAttributes(rttr, cri);
		return "redirect:list";
	}
}