package com.unknown.service;

import java.util.List;

import com.unknown.model.Criteria;
import com.unknown.model.QNACategoryVO;
import com.unknown.model.QNAVO;

public interface QNAService {
public void register(QNAVO qnaVO);
	
	public QNAVO get(Long qnaId);
	
	public boolean modify(QNAVO qnaVO);
	   
	public boolean remove(Long qnaId);
	   
	public List<QNAVO> getList(Criteria cri);
	
	public int getTotal(Criteria cri);

	

	public List<QNACategoryVO> getAllCategories();

	QNACategoryVO getCategoryById(Long categoryId);
}
