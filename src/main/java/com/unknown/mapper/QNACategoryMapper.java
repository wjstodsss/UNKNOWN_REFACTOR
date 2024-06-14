package com.unknown.mapper;

import java.util.List;

import com.unknown.model.QNACategoryVO;

public interface QNACategoryMapper {
	
	List<QNACategoryVO> getAllCategory();
	
	QNACategoryVO getCategoryById(Long categoryId);

	
}
