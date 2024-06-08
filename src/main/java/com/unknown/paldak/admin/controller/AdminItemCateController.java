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
import com.unknown.paldak.admin.domain.ItemCateVO;
import com.unknown.paldak.admin.service.BaseService;
import com.unknown.paldak.admin.service.AdminItemCateServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin/itemCate/*")
@RequiredArgsConstructor
public class AdminItemCateController {

	private final BaseService<ItemCateVO> itemCateService;
	private final AdminItemCateServiceImpl itemCateListService;
	
	@GetMapping(value = "/select/{page}/{cateCode}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Map<String, Object>> getList(@PathVariable("cateCode") String cateCode, @PathVariable("page") int page) {
	    Map<String, Object> responseData = new HashMap<>();
	    String cateParent = cateCode;
	    try {
	    	System.out.println(cateParent);
	        Criteria cri = new Criteria(page, 10);
	        System.out.println(cri);
	        List<ItemCateVO> list = itemCateListService.getListByCateParent(cri, cateParent);
	        list.forEach(i -> System.out.println(i));
	        int total = itemCateListService.getTotalByCateParent(cri, cateParent);
	        System.out.println(total);
	        PageDTO pageMaker = new PageDTO(cri, total);
	        System.out.println(pageMaker + "0000000000000000000000000000000000000");
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
		List<ItemCateVO> list = itemCateService.getList(cri);
		list.forEach(itemCateVO -> System.out.println(itemCateVO + "kkkkkkkkkkkkkkkk"));
		model.addAttribute("itemCates", list);
        int total = itemCateService.getTotal(cri);
        model.addAttribute("pageMaker", new PageDTO(cri, total));
        return "admin/itemCate";
	}

	
	@GetMapping("/descList")
	public String descList(Criteria cri, Model model) {
		List<ItemCateVO> list = itemCateService.getDescList(cri);
		list.forEach(itemCateVO -> System.out.println(itemCateVO));
		model.addAttribute("itemCates", list);
        int total = itemCateService.getTotal(cri);
        model.addAttribute("pageMaker", new PageDTO(cri, total));
        return "admin/itemCate";
	}
	

	@PostMapping("/register")
	public String register(Model model, ItemCateVO itemCateVO, RedirectAttributes rttr) {
	    itemCateService.register(itemCateVO);
	    System.out.println("kkkfsdfsdfsfsdfk");
	    rttr.addFlashAttribute("result", itemCateVO.getCateCode());
	    return "redirect:descList";
	}
	


	@GetMapping(value = "/get/{cateCode}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ItemCateVO> get(@PathVariable("cateCode") String cateCode) {
		System.out.println(cateCode);
		ItemCateVO item = itemCateListService.getByStringId(cateCode);
		System.out.println(item);
		return new ResponseEntity<>(item, HttpStatus.OK);
	}


	@PostMapping("/modify")
	public String modify(ItemCateVO itemCateVO, @ModelAttribute("cri") Criteria cri, @RequestParam("currentPath") String currentPath, RedirectAttributes rttr) {
	    if (itemCateService.modify(itemCateVO)) {
	        rttr.addFlashAttribute("result", "success");
	    }
	    rttr.addAttribute("pageNum", cri.getPageNum());
	    rttr.addAttribute("amount", cri.getAmount());
	    return "redirect:" + currentPath;
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("cateCode") String cateCode, 
	                     @ModelAttribute("cri") Criteria cri, 
	                     @RequestParam("currentPath") String currentPath, 
	                     RedirectAttributes rttr) {
	    try {
	        if (itemCateListService.removeByStringId(cateCode)) {
	            rttr.addFlashAttribute("result", "success");
	        }
	        System.out.println(currentPath + "lldlkddkdkdkdk");
	        rttr.addAttribute("pageNum", cri.getPageNum());
	        rttr.addAttribute("amount", cri.getAmount());
	        return "redirect:" + currentPath;
	    } catch (Exception e) {
	        System.out.println("lkhjhkkj");
	        rttr.addFlashAttribute("errorMessage", "An error occurred while deleting the category.");
	        rttr.addFlashAttribute("previousUrl", "itemCate/" + currentPath);
	        return "redirect:/admin/error";
	    }
	}
	
	


}