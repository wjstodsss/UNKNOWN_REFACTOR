package com.unknown.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unknown.mapper.BenefitsMapper;
import com.unknown.model.BenefitsVO;
import com.unknown.model.Criteria;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class BenefitsServiceImpl implements BenefitsService {

	@Autowired
	private BenefitsMapper mapper;
	
	@Override
	public BenefitsVO get(Long noticeId) {
		 log.info("get....." + noticeId);
	      return mapper.read(noticeId);
	}

	@Override
	public boolean modify(BenefitsVO benefits) {
		log.info("modify.... " + benefits);
	     return mapper.update(benefits)==1;
	}


	@Override
	public boolean remove(Long noticeId) {
		log.info("remove...." + noticeId);
	     return mapper.delete(noticeId)==1;
	}


	@Override
	public List<BenefitsVO> getList(Criteria cri) {
		log.info("getList.....");
		//return mapper.getList();
		System.out.println("ddddddd");
		return mapper.getListWithPaging(cri);
	}

	@Override
	public void register(BenefitsVO benefits) {
		log.info("register.... " + benefits);
		mapper.insertSelectKey(benefits);
		
	}
	
	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotalCount(cri);
	}
	
}
