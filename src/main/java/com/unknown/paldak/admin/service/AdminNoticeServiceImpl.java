package com.unknown.paldak.admin.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.common.domain.PageDTO;
import com.unknown.paldak.admin.domain.NoticeVO;
import com.unknown.paldak.admin.mapper.AdminNoticeMapper;
import com.unknown.paldak.admin.util.AdminFileUploadManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@RequiredArgsConstructor
public class AdminNoticeServiceImpl implements BaseServiceWithFile<NoticeVO>{

	private final AdminFileUploadManager fileUploadManager;
	private final AdminNoticeMapper mapper;

	@Override
	public void register(MultipartFile[] uploadFile, NoticeVO noticeVO) {
		if (!uploadFile[0].isEmpty()) { 
			String imageURL = fileUploadManager.uploadFiles(uploadFile).get("imageURLs");
			noticeVO.setNoticeImageURL(imageURL);
		}
		
		log.info("register... " + noticeVO);
		mapper.insertSelectKey(noticeVO);
		
	}

	@Override
	public NoticeVO get(Long noticeId) {
		log.info("get..." + noticeId);	
		return mapper.read(noticeId);
	}

	@Override
	public boolean modify(MultipartFile[] uploadFile, NoticeVO noticeVO) {
		if (!uploadFile[0].isEmpty()) { 
			String imageURL = fileUploadManager.uploadFiles(uploadFile).get("imageURLs");
			noticeVO.setNoticeImageURL(imageURL);
		}
		
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
