package com.unknown.mapper;

import java.util.List;
import java.util.Map;

import com.unknown.model.BenefitsVO;
import com.unknown.model.Criteria;

public interface BenefitsMapper {
	
    public List<BenefitsVO> getList();
    
    public void insert(BenefitsVO benefits);
	
	public void insertSelectKey(BenefitsVO benefits);
	
	public BenefitsVO read(Long benefitsId);
	
	public int delete(Long benefitsId);
	
	public int update(BenefitsVO benefits);
	
	public List<BenefitsVO> getListWithPaging(Criteria cri);
	
	public int getTotalCount(Criteria cri);
	
	public List<BenefitsVO> searchTest(Map<String, Map<String, String>> map);
}
