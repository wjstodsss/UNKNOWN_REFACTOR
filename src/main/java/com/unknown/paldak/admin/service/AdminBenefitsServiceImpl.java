package com.unknown.paldak.admin.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unknown.paldak.admin.common.domain.Criteria;

import com.unknown.paldak.admin.domain.BenefitsVO;
import com.unknown.paldak.admin.mapper.AdminBenefitsMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class AdminBenefitsServiceImpl implements BaseService<BenefitsVO>{
    
	@Autowired
	private AdminBenefitsMapper mapper;

	@Override
	public void register(BenefitsVO benefitsVO) {
		
		mapper.insertSelectKey(benefitsVO);
		
	}

	@Override
	public BenefitsVO get(Long benefitsId) {
		return mapper.read(benefitsId);
	}

	@Override
	public boolean modify(BenefitsVO benefitsVO) {
		System.out.println("kkkkkkkkkkddddddddddddkkkkkkkkkkkkkkk" + benefitsVO);
		int result = mapper.update(benefitsVO);
		return result==1;
	}

	@Override
	public boolean remove(Long benefitsId) {
		log.info("remove ... " + benefitsId);
		return mapper.delete(benefitsId)==1;
	}

	@Override
	public List<BenefitsVO> getList(Criteria cri) {
		return mapper.getListWithPaging(cri);
	}
	
	@Override
	public List<BenefitsVO> getDescList(Criteria cri) {
		return mapper.getDescListWithPaging(cri);
	}
	
	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotalCount(cri);
	}
	
	
}
