package com.unknown.paldak.admin.service;



import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.domain.QNAVO;
import com.unknown.paldak.admin.mapper.AdminQnaMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class AdminQnaServiceImpl implements BaseService<QNAVO>{
    
	@Autowired
	private AdminQnaMapper mapper;

	@Override
	public void register(QNAVO qnaVO) {
		log.info("register... " + qnaVO);
		mapper.insertSelectKey(qnaVO);
		
	}

	@Override
	public QNAVO get(Long qnaId) {
		log.info("get..." + qnaId);	
		return mapper.read(qnaId);
	}

	@Override
	public boolean modify(QNAVO qnaVO) {
		LocalDateTime now = LocalDateTime.now();
		Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
		qnaVO.setQnaUpdateDate(date);
		return mapper.update(qnaVO)==1;
	}

	@Override
	public boolean remove(Long qnaId) {
		log.info("remove ... " + qnaId);
		return mapper.delete(qnaId)==1;
	}

	@Override
	public List<QNAVO> getList(Criteria cri) {
		System.out.println(cri);
		return mapper.getListWithPaging(cri);
	}
	
	@Override
	public List<QNAVO> getDescList(Criteria cri) {
		System.out.println(cri);
		return mapper.getDescListWithPaging(cri);
	}
	
	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotalCount(cri);
	}
	
	
}