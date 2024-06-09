package com.unknown.paldak.admin.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.common.domain.PageDTO;
import com.unknown.paldak.admin.domain.NoticeVO;
import com.unknown.paldak.admin.mapper.AdminNoticeMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class AdminNoticeServiceImpl implements BaseService<NoticeVO>{
    
	@Autowired
	private AdminNoticeMapper mapper;

	@Override
	public void register(NoticeVO noticeVO) {
		log.info("register... " + noticeVO);
		mapper.insertSelectKey(noticeVO);
		
	}

	@Override
	public NoticeVO get(Long noticeId) {
		log.info("get..." + noticeId);	
		return mapper.read(noticeId);
	}

	@Override
	public boolean modify(NoticeVO noticeVO) {
		LocalDateTime now = LocalDateTime.now();
		Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
		noticeVO.setNoticeUpdateDate(date);
		return mapper.update(noticeVO)==1;
	}

	@Override
	public boolean remove(Long noticeId) {
		log.info("remove ... " + noticeId);
		return mapper.delete(noticeId)==1;
	}

	@Override
	public List<NoticeVO> getList(Criteria cri) {
		System.out.println(cri);
		return mapper.getListWithPaging(cri);
	}
	
	@Override
	public List<NoticeVO> getDescList(Criteria cri) {
		return mapper.getDescListWithPaging(cri);
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
	
	
}
