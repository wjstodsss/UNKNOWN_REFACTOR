package com.unknown.mapper;

import java.util.List;

import com.unknown.model.FAQCategoryVO;

public interface FAQCategoryMapper {
	List<FAQCategoryVO> getAllCategory();
	
	FAQCategoryVO getCategoryById(Long categoryId);

}
