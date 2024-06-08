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
import com.unknown.paldak.admin.domain.StockInfoVO;
import com.unknown.paldak.admin.service.BaseService;
import com.unknown.paldak.admin.service.AdminStockInfoServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin/item/stock/*")
@RequiredArgsConstructor
public class AdminStockInfoController {

	private final BaseService<StockInfoVO> stockInfoService;
	private final AdminStockInfoServiceImpl stockInfoServiceUtil;
    

	@GetMapping("/list")
	public String list(Criteria cri, Model model) {
		System.out.println("jlkjlkjl");
		System.out.println(cri.getPageNum()+"1321321");

		List<StockInfoVO> list = stockInfoService.getList(cri);
		list.forEach(stockInfoVO -> System.out.println(stockInfoVO + "kkkkkkkkkkkkkkkk"));
		model.addAttribute("items", list);
		
	
        int total = stockInfoService.getTotal(cri);
        
        model.addAttribute("pageMaker", new PageDTO(cri, total));     
        return "admin/itemStock";
	}

	
	@GetMapping("/descList")
	public String descList(Criteria cri, Model model) {
		System.out.println("1");
		System.out.println(cri);
		System.out.println("cricricricrircicicicicicicici" + cri);
	
		
		List<StockInfoVO> list = stockInfoService.getDescList(cri);
		list.forEach(stockInfoVO -> System.out.println(stockInfoVO + "zzzzzzzzzzzzzzzz"));
		list.forEach(stockInfoVO -> System.out.println(stockInfoVO));
		model.addAttribute("items", list);
		
		
        int total = stockInfoService.getTotal(cri);
        
        model.addAttribute("pageMaker", new PageDTO(cri, total));
        return "admin/itemStock";
	}
	

	@PostMapping("/register")
	public String stockOrderReg (@RequestParam("registerType") String registerType, Model model, StockInfoVO stockInfoVO) {
		System.out.println(registerType + "sadfffffffffffffffffffffffffffffff");
		if("stockOrderReg".equals(registerType)) {
			System.out.println(stockInfoVO);
			stockInfoServiceUtil.stockOrderReg(stockInfoVO);
		    return "redirect:descList";
		} else {
			System.out.println(stockInfoVO);
			stockInfoServiceUtil.receivedReg(stockInfoVO);
		    return "redirect:descList";
		}
		
	}
	

	@GetMapping(value = "/get/{stockOrderId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, Object>> get(@PathVariable("stockOrderId") long stockOrderId) {
        StockInfoVO stockInfo = stockInfoService.get(stockOrderId);
        if (stockInfo != null) {
            System.out.println("Success: " + stockInfo);
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("stockInfo", stockInfo);
            return ResponseEntity.ok(responseData);
        } else {
            System.out.println("ë°?ì£? ??ì²???ê²? ???? " + stockOrderId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


	
	@PostMapping("/modify")
	public String modify(StockInfoVO stockInfoVO, @ModelAttribute("cri") Criteria cri, @RequestParam("currentPath") String currentPath, RedirectAttributes rttr) {
	    if (stockInfoService.modify(stockInfoVO)) {
	        rttr.addFlashAttribute("result", "success");
	    }
	    rttr.addAttribute("pageNum", cri.getPageNum());
	    rttr.addAttribute("amount", cri.getAmount());
	    return "redirect:" + currentPath;
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("itemId") Long itemId, @ModelAttribute("cri") Criteria cri, @RequestParam("currentPath") String currentPath, RedirectAttributes rttr) {
		if (stockInfoService.remove(itemId)) {
			rttr.addFlashAttribute("result", "success");
		}
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		return "redirect:" + currentPath;
	}
}