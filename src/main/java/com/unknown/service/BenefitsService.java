package com.unknown.service;

import java.util.List;

import com.unknown.model.BenefitsVO;
import com.unknown.model.Criteria;

public interface BenefitsService {
	public void register(BenefitsVO benefits);
	
	public BenefitsVO get(Long benefitsId);
	
	public boolean modify(BenefitsVO benefits);
	   
	public boolean remove(Long benefitsId);
	   
	public List<BenefitsVO> getList(Criteria cri);
	
	public int getTotal(Criteria cri);
}
