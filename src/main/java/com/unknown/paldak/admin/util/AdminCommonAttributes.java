package com.unknown.paldak.admin.util;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unknown.paldak.admin.common.domain.Criteria;

@Component
public class AdminCommonAttributes {
	public void addCommonAttributes(RedirectAttributes rttr, Criteria cri, String sortType) {
	    rttr.addAttribute("sortType", sortType);
	    rttr.addAttribute("pageNum", cri.getPageNum());
	    rttr.addAttribute("amount", cri.getAmount());
	}
}
