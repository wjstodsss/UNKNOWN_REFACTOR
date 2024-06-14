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
import com.unknown.paldak.admin.domain.QNAReplyVO;
import com.unknown.paldak.admin.domain.QNAVO;
import com.unknown.paldak.admin.service.AdminQnaReplyServiceImpl;
import com.unknown.paldak.admin.service.BaseServiceWithFile;
import com.unknown.paldak.admin.util.AdminCommonAttributes;
import com.unknown.paldak.admin.util.AdminSortOrder;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin/qna/*")
@RequiredArgsConstructor
public class AdminQnaController {

	private final BaseServiceWithFile<QNAVO> qnaService;
	private final AdminQnaReplyServiceImpl replyService;
    private final AdminCommonAttributes addAttribute;

	@GetMapping("/list")
	public String list(Criteria cri, Model model) {
		List<QNAVO> list = qnaService.getList(cri);
		model.addAttribute("qnas", list);
		model.addAttribute("pageMaker", qnaService.getPageMaker(cri));
        return "admin/qna";
	}

	
	@PostMapping("/register")
	public String register(
		@RequestParam("uploadFile") MultipartFile[] uploadFile, 
		QNAVO qnaVO,
		@ModelAttribute("cri") Criteria cri,
		RedirectAttributes rttr) {
		
		cri.setSortType(AdminSortOrder.DESC.getValue());
        cri.setPageNum(AdminSortOrder.FIRST_PAGE_NUM);

	    qnaService.register(uploadFile, qnaVO);

	    addAttribute.addCommonAttributes(rttr, cri);
	    return "redirect:list";
	}

	@GetMapping(value = "/get/{qnaId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String, Object>> get(@PathVariable("qnaId") Long qnaId) {
	    QNAVO qna = qnaService.get(qnaId);
	    
	    QNAReplyVO reply = replyService.getByQnaId(qnaId); 
	    
	    Map<String, Object> responseData = new HashMap<>();
	    responseData.put("qna", qna);
	    responseData.put("reply", reply);

	    return new ResponseEntity<>(responseData, HttpStatus.OK);
	}


	
	@PostMapping("/modify")
	public String modify(
		@RequestParam("uploadFile") MultipartFile[] uploadFile, 
		QNAVO qnaVO, 
		@ModelAttribute("cri") Criteria cri, 
		QNAReplyVO replyVO,
		RedirectAttributes rttr) {
        

        if(replyVO.getReplyId() == null){
        	replyService.register(replyVO);
        }
        
        replyService.modify(replyVO);

	    boolean result = qnaService.modify(uploadFile,qnaVO);
		if (!result) {
			rttr.addFlashAttribute("error", "QNA 수정에 실패했습니다.");
	    }
		addAttribute.addCommonAttributes(rttr, cri);
		return "redirect:list";
	}
	
	@PostMapping("/remove")
	public String remove(
		@RequestParam("qnaId") Long qnaId, 
		@ModelAttribute("cri") Criteria cri,
		RedirectAttributes rttr) {

		boolean result = qnaService.remove(qnaId);
		if (!result) {
			rttr.addFlashAttribute("error", "QNA 삭제에 실패했습니다.");
	    } 
		addAttribute.addCommonAttributes(rttr, cri);
		return "redirect:list";
	}
}