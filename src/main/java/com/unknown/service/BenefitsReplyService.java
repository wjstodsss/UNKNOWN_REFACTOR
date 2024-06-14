package com.unknown.service;

import java.util.List;

import com.unknown.model.BenefitsReplyVO;
import com.unknown.model.Criteria;

public interface BenefitsReplyService {
	
	
	public int register(BenefitsReplyVO vo);
	
	public BenefitsReplyVO get(Long benefitsReplyId);
	
	public int remove(Long benefitsReplyId);
	
	public List<BenefitsReplyVO> getList();
	
	public int getTotal(Criteria cri);
}
