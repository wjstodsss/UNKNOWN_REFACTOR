package com.unknown.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unknown.model.BoardVO;
import com.unknown.model.Criteria;
import com.unknown.model.PageDTO;
import com.unknown.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
@RequiredArgsConstructor
public class BoardController {
	private final BoardService boardService;
	
	@GetMapping("/notice")
	public void boarder(Criteria cri, Model model) {
		log.info("list...............");
		List<BoardVO> list = boardService.getList(cri);

		list.forEach(board -> log.info(board));
		model.addAttribute("list", list);
		int total = boardService.getTotal(cri);
		model.addAttribute("pageMaker", new PageDTO(cri, total)); 
	}
	
	@GetMapping("/register")
	public void register() {
		
	}
	
	
	@PostMapping("/register")
	public String register(BoardVO vo, RedirectAttributes rttr) {
		log.info("register......." + vo);

		boardService.register(vo);

		rttr.addFlashAttribute("result", vo.getNoticeId());
		return "redirect:/board/notice";
	}
	
	
	@PostMapping("/modify")
    public String modify(BoardVO board,@ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
   	 log.info("modify:" + board);
   	 if(boardService.modify(board)) {
   		 rttr.addFlashAttribute("result", "success");
   	 }
   	rttr.addAttribute("pageNum", cri.getPageNum());
	rttr.addAttribute("amount", cri.getAmount());
	rttr.addAttribute("type", cri.getType());
	rttr.addAttribute("keyword", cri.getKeyword());
   	 return "redirect:/board/notice";
    }
	
	
	
	@GetMapping({"/get", "/modify"})
	public void get(@RequestParam("noticeId") Long noticeId,@ModelAttribute("cri") Criteria cri, Model model) {
		model.addAttribute("board", boardService.get(noticeId));
	}
	
	
	@PostMapping("/remove")
    public String remove(@RequestParam("noticeId") Long noticeId, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr ) {
   	 log.info("remove...." +noticeId);
   	 if(boardService.remove(noticeId)) {
   		 rttr.addFlashAttribute("result", "success");
   	 }
   	rttr.addAttribute("pageNum", cri.getPageNum());
	rttr.addAttribute("amount", cri.getAmount());
	rttr.addAttribute("type", cri.getType());
	rttr.addAttribute("keyword", cri.getKeyword());
   	 return "redirect:/board/notice";
    }
}
