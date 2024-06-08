package com.unknown.paldak.admin.controller;

import java.util.Collections;
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
import com.unknown.paldak.admin.domain.BrandVO;
import com.unknown.paldak.admin.service.BaseService;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("admin/brand/*")
@RequiredArgsConstructor
public class AdminBrandController {

	private final BaseService<BrandVO> brandService;
	
	
	@GetMapping(value = "/select/{page}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Map<String, Object>> getList(@PathVariable("page") int page) {
	    Map<String, Object> responseData = new HashMap<>();
	    
	    try {
	        Criteria cri = new Criteria(page, 10);
	        List<BrandVO> list = brandService.getList(cri);
	        int total = brandService.getTotal(cri);
	        PageDTO pageMaker = new PageDTO(cri, total);

	        responseData.put("list", list);
	        responseData.put("pageMaker", pageMaker);

	        return ResponseEntity.ok(responseData);
	    } catch (Exception e) {
	        // ë¡?ê·¸ë?? ?¨ê?????.
	        e.printStackTrace();
	        // ???? ë°??? ?? ?????? ???µì?? ë°????©ë????.
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body(Collections.singletonMap("error", "??ë²? ?¤ë?ê°? ë°??????µë????."));
	    }
	}


	
	@GetMapping("/list")
	public String list(Criteria cri, Model model) {
		List<BrandVO> list = brandService.getList(cri);
		list.forEach(brandVO -> System.out.println(brandVO + "kkkkkkkkkkkkkkkk"));
		model.addAttribute("brands", list);
        int total = brandService.getTotal(cri);
        model.addAttribute("pageMaker", new PageDTO(cri, total));
        return "admin/brand";
	}

	
	@GetMapping("/descList")
	public String descList(Criteria cri, Model model) {
		List<BrandVO> list = brandService.getDescList(cri);
		list.forEach(brandVO -> System.out.println(brandVO));
		model.addAttribute("brands", list);
		
        int total = brandService.getTotal(cri);
        
        model.addAttribute("pageMaker", new PageDTO(cri, total));
        return "admin/brand";
	}
	

	@PostMapping("/register")
	public String register(Model model, BrandVO brandVO, RedirectAttributes rttr) {
	    brandService.register(brandVO);
	    System.out.println("kkkfsdfsdfsfsdfk");
	    rttr.addFlashAttribute("result", brandVO.getBrandId());
	    return "redirect:descList";
	}

	@GetMapping(value = "/get/{brandId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<BrandVO> get(@PathVariable("brandId") Long brandId) {
		return new ResponseEntity<>(brandService.get(brandId), HttpStatus.OK);
	}


	@PostMapping("/modify")
	public String modify(BrandVO brandVO, @ModelAttribute("cri") Criteria cri, @RequestParam("currentPath") String currentPath, RedirectAttributes rttr) {
	    if (brandService.modify(brandVO)) {
	        rttr.addFlashAttribute("result", "success");
	    }
	    rttr.addAttribute("pageNum", cri.getPageNum());
	    rttr.addAttribute("amount", cri.getAmount());
	    return "redirect:" + currentPath;
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("brandId") Long brandId, @ModelAttribute("cri") Criteria cri, @RequestParam("currentPath") String currentPath, RedirectAttributes rttr) {
		if (brandService.remove(brandId)) {
			rttr.addFlashAttribute("result", "success");
		}
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		return "redirect:" + currentPath;
	}
	
	
}