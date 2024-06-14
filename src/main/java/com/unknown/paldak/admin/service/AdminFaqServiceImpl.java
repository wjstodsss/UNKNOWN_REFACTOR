package com.unknown.paldak.admin.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.common.domain.PageDTO;
import com.unknown.paldak.admin.domain.FAQVO;
import com.unknown.paldak.admin.mapper.AdminFaqMapper;
import com.unknown.paldak.admin.util.AdminFileUploadManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@RequiredArgsConstructor
public class AdminFaqServiceImpl implements BaseServiceWithFile<FAQVO>{
    
    private final AdminFileUploadManager fileUploadManager;
	private final AdminFaqMapper mapper;

	@Override
	public void register(MultipartFile[] uploadFile, FAQVO faqVO) {
		if (!uploadFile[0].isEmpty()) { 
			String imageURL = fileUploadManager.uploadFiles(uploadFile).get("imageURLs");
			faqVO.setFaqImageURL(imageURL);
		}
		
		log.info("register... " + faqVO);
		mapper.insertSelectKey(faqVO);
	}

	@Override
	public FAQVO get(Long faqId) {
	    FAQVO result = mapper.read(faqId);
	    log.info("get..." + result);
	    return result;
	}


	@Override
	public boolean modify(MultipartFile[] uploadFile, FAQVO faqVO) {
		log.info("modify" + faqVO);
		if (!uploadFile[0].isEmpty()) { 
			String imageURL = fileUploadManager.uploadFiles(uploadFile).get("imageURLs");
			faqVO.setFaqImageURL(imageURL);
		}
		LocalDateTime now = LocalDateTime.now();
		Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
		faqVO.setFaqUpdateDate(date);
		return mapper.update(faqVO)==1;
	}

	@Override
	public boolean remove(Long faqId) {
		log.info("remove ... " + faqId);
		return mapper.delete(faqId)==1;
	}

	@Override
	public List<FAQVO> getList(Criteria cri) {
	    List<FAQVO> result = mapper.getListWithPaging(cri);
	    return result;
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