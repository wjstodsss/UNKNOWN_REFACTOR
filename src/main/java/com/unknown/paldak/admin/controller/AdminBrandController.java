package com.unknown.paldak.admin.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
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
import com.unknown.paldak.admin.domain.BrandVO;
import com.unknown.paldak.admin.service.BaseServiceDefault;
import com.unknown.paldak.admin.util.AdminCommonAttributes;
import com.unknown.paldak.admin.util.AdminSortOrder;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("admin/brand/*")
@RequiredArgsConstructor
public class AdminBrandController {
	private final BaseServiceDefault<BrandVO> brandService;
	private final AdminCommonAttributes addAttribute;
	
	
	@GetMapping(value = "/select/{page}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Map<String, Object>> getList(@PathVariable("page") int page) {
	    Map<String, Object> responseData = new HashMap<>();
	    
	    try {
	        Criteria cri = new Criteria(page, 10);
	        List<BrandVO> list = brandService.getList(cri);
	        responseData.put("list", list);
	        responseData.put("pageMaker", brandService.getPageMaker(cri));

	        return ResponseEntity.ok(responseData);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body(Collections.singletonMap("error", "??�? ?��?�? �??????��????."));
	    }
	}


	
	@GetMapping("/list")
	public String list(Criteria cri, Model model) {
		List<BrandVO> list = brandService.getList(cri);
		list.forEach(brandVO -> System.out.println(brandVO));
		model.addAttribute("brands", list);
        model.addAttribute("pageMaker", brandService.getPageMaker(cri));
        return "admin/brand";
	}


	@PostMapping("/register")
	public String register(
		Model model, 
		BrandVO brandVO, 
		RedirectAttributes rttr, 
		@ModelAttribute("cri") Criteria cri) {
		
		cri.setSortType(AdminSortOrder.DESC.getValue());
		cri.setPageNum(AdminSortOrder.FIRST_PAGE_NUM);
		
	    brandService.register(brandVO);
	    addAttribute.addCommonAttributes(rttr, cri);
	    return "redirect:list";
	}

	@GetMapping(value = "/get/{brandId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<BrandVO> get(@PathVariable("brandId") Long brandId) {
		return new ResponseEntity<>(brandService.get(brandId), HttpStatus.OK);
	}


	@PostMapping("/modify")
	public String modify(BrandVO brandVO, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
	    if (brandService.modify(brandVO)) {
	        rttr.addFlashAttribute("result", "success");
	    }
	    addAttribute.addCommonAttributes(rttr, cri);
	    return "redirect:list";
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("brandId") Long brandId, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
	    try {
	        if (brandService.remove(brandId)) {
	            rttr.addFlashAttribute("result", "success");
	        }
	        addAttribute.addCommonAttributes(rttr, cri);
	        return "redirect:list";
	    } catch (DataIntegrityViolationException e) {
	        // ORA-02292 �޽����� �˻��Ͽ� ����ڿ��� �ȳ� �޽����� ����
	        if (e.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException &&
	            e.getCause().getMessage().contains("ORA-02292")) {
	            rttr.addFlashAttribute("errorMessage", "�ش� �귣�带 ����ϴ� �������� �����մϴ�.");
	        } else {
	            rttr.addFlashAttribute("errorMessage", "������ ���Ἲ �������� ���� ������ �� �����ϴ�.");
	        }
	        rttr.addFlashAttribute("previousUrl", "/admin/brand/list");
	        return "redirect:/admin/error";
	    } catch (Exception e) {
	        rttr.addFlashAttribute("errorMessage", "�� �� ���� ������ �߻��߽��ϴ�.");
	        rttr.addFlashAttribute("previousUrl", "/admin/brand/list");
	        return "redirect:/admin/error";
	    }
	}


	
	
}