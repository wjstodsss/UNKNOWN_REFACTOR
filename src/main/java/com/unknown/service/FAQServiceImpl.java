package com.unknown.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unknown.model.Criteria;
import com.unknown.model.FAQCategoryVO;
import com.unknown.model.FAQVO;
import com.unknown.mapper.FAQCategoryMapper;
import com.unknown.mapper.FAQMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class FAQServiceImpl implements FAQService {

	@Autowired
	private FAQMapper mapper;
	private FAQCategoryMapper faqCategoryMapper;
	
	
	@Override
	public FAQVO get(Long faqId) {
		 log.info("get....." + faqId);
	      return mapper.read(faqId);
	}

	@Override
	public boolean modify(FAQVO FAQ) {
		log.info("modify.... " + FAQ);
	     return mapper.update(FAQ)==1;
	}


	@Override
	public boolean remove(Long faqId) {
		log.info("remove...." + faqId);
	     return mapper.delete(faqId)==1;
	}



	@Override
	public List<FAQVO> getList(Criteria cri) {
		log.info("getList.....");
		return mapper.getListWithPaging(cri);
	}

	@Override
	public void register(FAQVO FAQ) {
		log.info("register.... " + FAQ);
		mapper.insertSelectKey(FAQ);
		
	}
	
	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotalCount(cri);
	}
	@Override
    public List<FAQCategoryVO> getAllCategories() {
        return faqCategoryMapper.getAllCategory();
    }
	@Override
    public FAQCategoryVO getCategoryById(Long categoryId) {
        return faqCategoryMapper.getCategoryById(categoryId);
    }

	
}
