package com.unknown.paldak.admin.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.common.domain.PageDTO;
import com.unknown.paldak.admin.domain.BenefitsVO;
import com.unknown.paldak.admin.mapper.AdminBenefitsMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class AdminBenefitsServiceImpl implements BaseServiceDefault<BenefitsVO>{
    
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
	public int getTotal(Criteria cri) {
		return mapper.getTotalCount(cri);
	}
	
	@Override
    public PageDTO getPageMaker(Criteria cri) {
        int total = getTotal(cri);
        log.info("Creating PageDTO for criteria: " + cri + ", total: " + total);
        return new PageDTO(cri, total);
    }
	
	public List<BenefitsVO> getBenefitsList(Criteria cri) {
        List<BenefitsVO> list = getList(cri);
        log.info("Stock list retrieved: " + list.size() + " items");
        return list;
        
    }
	
}
