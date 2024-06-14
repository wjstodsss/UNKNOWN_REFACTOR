package com.unknown.paldak.admin.service;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.common.domain.PageDTO;
import com.unknown.paldak.admin.domain.BenefitsReplyVO;
import com.unknown.paldak.admin.domain.OrderItemVO;
import com.unknown.paldak.admin.mapper.AdminBenefitsReplyMapper;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@RequiredArgsConstructor
public class AdminBenefitsReplyServiceImpl implements BaseServiceDefault<BenefitsReplyVO>{
	private final char YES_ANSWER = 'Y'; 
    
	private final AdminBenefitsReplyMapper mapper;

	@Override
	public void register(BenefitsReplyVO replyVO) {
		if(replyVO.getReply() != null && replyVO.getReply().length()!=0) {
			replyVO.setAnswer(YES_ANSWER);
		}
		mapper.insertSelectKey(replyVO);
		
	}

	@Override
	public BenefitsReplyVO get(Long replyId) {
		return mapper.read(replyId);
	}

	
	@Override
	public boolean modify(BenefitsReplyVO replyVO) {
		LocalDateTime now = LocalDateTime.now();
		Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
		replyVO.setReplyUpdateDate(date);
		return mapper.update(replyVO)==1;
	}

	@Override
	public boolean remove(Long benefitsId) {
		log.info("remove ... " + benefitsId);
		return mapper.delete(benefitsId)==1;
	}

	@Override
	public List<BenefitsReplyVO> getList(Criteria cri) {
		System.out.println(cri+"reply");
		return mapper.getList();
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

	public BenefitsReplyVO getByBenefitsId(Long benefitsId) {
		return mapper.readByBenefitsId(benefitsId);
	}
	
	
	
}