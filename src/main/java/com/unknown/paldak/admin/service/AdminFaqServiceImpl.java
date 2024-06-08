package com.unknown.paldak.admin.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.domain.FAQVO;

import com.unknown.paldak.admin.mapper.AdminFaqMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class AdminFaqServiceImpl implements BaseService<FAQVO>{
    
	@Autowired
	private AdminFaqMapper mapper;

	@Override
	public void register(FAQVO faqVO) {
		log.info("register... " + faqVO);
		mapper.insertSelectKey(faqVO);
		
	}

	@Override
	public FAQVO get(Long faqId) {
		log.info("get..." + faqId);	
		return mapper.read(faqId);
	}

	@Override
	public boolean modify(FAQVO faqVO) {
		LocalDateTime now = LocalDateTime.now();
		Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
		faqVO.setFaqUpdateDate(date);
		return mapper.update(faqVO)==1;
	}

	@Override
	public boolean remove(Long faqId) {
		log.info("remove ... " + faqId);
		return mapper.delete(faqId)==1;
	}

	@Override
	public List<FAQVO> getList(Criteria cri) {
		System.out.println(cri);
		return mapper.getListWithPaging(cri);
	}
	
	@Override
	public List<FAQVO> getDescList(Criteria cri) {
		return mapper.getDescListWithPaging(cri);
	}
	
	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotalCount(cri);
	}
	
	
}