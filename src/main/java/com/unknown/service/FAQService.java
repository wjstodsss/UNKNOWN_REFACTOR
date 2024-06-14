package com.unknown.service;

import java.util.List;

import com.unknown.model.Criteria;
import com.unknown.model.FAQCategoryVO;
import com.unknown.model.FAQVO;

public interface FAQService {
	public void register(FAQVO faqVO);
	
	public FAQVO get(Long faqId);
	
	public boolean modify(FAQVO faqVO);
	   
	public boolean remove(Long faqId);
	   
	public List<FAQVO> getList(Criteria cri);
	
	public int getTotal(Criteria cri);
	
	public List<FAQCategoryVO> getAllCategories();

	FAQCategoryVO getCategoryById(Long categoryId);
}
