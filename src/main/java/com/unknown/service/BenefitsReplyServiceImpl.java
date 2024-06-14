package com.unknown.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unknown.mapper.BenefitsReplyMapper;
import com.unknown.model.BenefitsReplyVO;
import com.unknown.model.Criteria;

import lombok.AllArgsConstructor;


@Service

@AllArgsConstructor
public class BenefitsReplyServiceImpl implements BenefitsReplyService {
	private BenefitsReplyMapper mapper;
	
	@Override
	public int register(BenefitsReplyVO vo) {
		return mapper.insert(vo);
	};
	
	@Override
	public BenefitsReplyVO get(Long benefitsReplyId) {
		return mapper.read(benefitsReplyId);
	};
	
	@Override
	public int remove(Long benefitsReplyId) {
		return mapper.remove(benefitsReplyId);
	}

	@Override
	public List<BenefitsReplyVO> getList() {
		System.out.println("sssssss");
		return mapper.getListWithPaging();
	};
	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotalCount(cri);
	}
}
