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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.domain.StockInfoVO;
import com.unknown.paldak.admin.service.AdminStockInfoServiceImpl;
import com.unknown.paldak.admin.service.BaseServiceDefault;
import com.unknown.paldak.admin.util.AdminCommonAttributes;
import com.unknown.paldak.admin.util.AdminSortOrder;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin/item/stock/*")
@RequiredArgsConstructor
public class AdminStockInfoController {

	private final BaseServiceDefault<StockInfoVO> stockInfoService;
	private final AdminStockInfoServiceImpl stockInfoServiceUtil;
	private final AdminCommonAttributes addAttribute;
    

	 @GetMapping("/list")
	    public String list(
	    	Criteria cri,
	    	Model model) {
		 
	        List<StockInfoVO> list = stockInfoServiceUtil.getStockList(cri);
	        model.addAttribute("items", list);
	        model.addAttribute("pageMaker", stockInfoService.getPageMaker(cri));
	        return "admin/itemStock";
	    }
	
	@PostMapping("/register")
	public String stockOrderReg (
		@RequestParam("registerType") String registerType,
		Criteria cri,
		RedirectAttributes rttr,
		StockInfoVO stockInfoVO) {
	
		if("stockOrderReg".equals(registerType)) {
			stockInfoServiceUtil.stockOrderReg(stockInfoVO);
		} else {
			stockInfoServiceUtil.receivedReg(stockInfoVO);
		}
		addAttribute.addCommonAttributes(rttr, cri);	  
	    return "redirect:list";
		
	}

	@GetMapping(value = "/getStockOrderQty/{stockOrderId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, Object>> getStockOrderQty(@PathVariable("stockOrderId") long stockOrderId) {
        StockInfoVO stockInfo = stockInfoService.get(stockOrderId);
        if (stockInfo != null) {
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("stockInfo", stockInfo);
            return ResponseEntity.ok(responseData);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}