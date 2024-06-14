package com.unknown.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unknown.model.FAQCategoryVO;
import com.unknown.mapper.FAQCategoryMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FAQCategoryService {
	@Autowired
	private FAQCategoryMapper faqCategoryMapper;
	
	
	public List<FAQCategoryVO> getAllCategory(){

		return faqCategoryMapper.getAllCategory();
	}
	

	public FAQCategoryVO getCategoryById(Long categoryId) {
		return faqCategoryMapper.getCategoryById(categoryId);
	}
}
