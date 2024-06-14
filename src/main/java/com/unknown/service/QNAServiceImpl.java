package com.unknown.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unknown.model.Criteria;
import com.unknown.model.QNACategoryVO;
import com.unknown.model.QNAVO;
import com.unknown.mapper.QNACategoryMapper;
import com.unknown.mapper.QNAMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class QNAServiceImpl implements QNAService {

	@Autowired
	private QNAMapper mapper;
	private QNACategoryMapper qnaCategoryMapper;
	
	@Override
	public QNAVO get(Long qnaId) {
		 log.info("get....." + qnaId);
	      return mapper.read(qnaId);
	}

	@Override
	public boolean modify(QNAVO qna) {
		log.info("modify.... " + qna);
	     return mapper.update(qna)==1;
	}


	@Override
	public boolean remove(Long qnaId) {
		log.info("remove...." + qnaId);
	     return mapper.delete(qnaId)==1;
	}



	@Override
	public List<QNAVO> getList(Criteria cri) {
		log.info("getList.....");
		return mapper.getListWithPaging(cri);
	}

	@Override
	public void register(QNAVO qna) {
		log.info("register.... " + qna);
		mapper.insertSelectKey(qna);
		
	}
	
	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotalCount(cri);
	}
	@Override
    public List<QNACategoryVO> getAllCategories() {
        return qnaCategoryMapper.getAllCategory();
    }

    
	
	@Override
    public QNACategoryVO getCategoryById(Long categoryId) {
        return qnaCategoryMapper.getCategoryById(categoryId);
    }
	
}
