package com.unknown.paldak.admin.service;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.common.domain.PageDTO;
import com.unknown.paldak.admin.domain.QNAVO;
import com.unknown.paldak.admin.mapper.AdminQnaMapper;
import com.unknown.paldak.admin.util.AdminFileUploadManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;



@Log4j
@Service
@RequiredArgsConstructor
public class AdminQnaServiceImpl implements BaseServiceWithFile<QNAVO>{
    
	private final AdminQnaMapper mapper;
    private final AdminFileUploadManager fileUploadManager;
    
	@Override
	public void register(MultipartFile[] uploadFile, QNAVO qnaVO) {
		if (!uploadFile[0].isEmpty()) { 
			String imageURL = fileUploadManager.uploadFiles(uploadFile).get("imageURLs");
			qnaVO.setQnaImageURL(imageURL);
		}
		
		log.info("register... " + qnaVO);
		mapper.insertSelectKey(qnaVO);
		
	}

	@Override
	public QNAVO get(Long qnaId) {
		log.info("get..." + qnaId);	
		return mapper.read(qnaId);
	}

	@Override
	public boolean modify(MultipartFile[] uploadFile, QNAVO qnaVO) {
		if (!uploadFile[0].isEmpty()) { 
			String imageURL = fileUploadManager.uploadFiles(uploadFile).get("imageURLs");
			qnaVO.setQnaImageURL(imageURL);
		}
		
		LocalDateTime now = LocalDateTime.now();
		Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
		qnaVO.setQnaUpdateDate(date);
		return mapper.update(qnaVO)==1;
	}

	@Override
	public boolean remove(Long qnaId) {
		log.info("remove ... " + qnaId);
		return mapper.delete(qnaId)==1;
	}

	@Override
	public List<QNAVO> getList(Criteria cri) {
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