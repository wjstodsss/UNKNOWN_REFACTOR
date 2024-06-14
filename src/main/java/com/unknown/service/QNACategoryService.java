package com.unknown.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unknown.model.QNACategoryVO;
import com.unknown.mapper.QNACategoryMapper;

import lombok.AllArgsConstructor;




@Service
@AllArgsConstructor
public class QNACategoryService {
	
	@Autowired
	private QNACategoryMapper qnaCategoryMapper;
	
	
	public List<QNACategoryVO> getAllCategory(){

		return qnaCategoryMapper.getAllCategory();
	}
	

	public QNACategoryVO getCategoryById(Long categoryId) {
		return qnaCategoryMapper.getCategoryById(categoryId);
	}
	
}
	
