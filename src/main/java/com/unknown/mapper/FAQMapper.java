package com.unknown.mapper;

import java.util.List;
import java.util.Map;

import com.unknown.model.Criteria;
import com.unknown.model.FAQVO;


public interface FAQMapper {

    public List<FAQVO> getList();
    
    public void insert(FAQVO faq);
	
	public void insertSelectKey(FAQVO faq);
	
	public FAQVO read(Long faqId);
	
	public int delete(Long faqId);
	
	public int update(FAQVO faq);
	
	public List<FAQVO> getListWithPaging(Criteria cri);
	
	public int getTotalCount(Criteria cri);
	
	public List<FAQVO> searchTest(Map<String, Map<String, String>> map);

}
