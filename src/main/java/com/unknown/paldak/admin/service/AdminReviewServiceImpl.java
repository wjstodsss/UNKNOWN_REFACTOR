package com.unknown.paldak.admin.service;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.common.domain.PageDTO;
import com.unknown.paldak.admin.domain.ReviewVO;
import com.unknown.paldak.admin.mapper.AdminReviewMapper;
import com.unknown.paldak.admin.util.AdminFileUploadManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@RequiredArgsConstructor
public class AdminReviewServiceImpl implements BaseServiceWithFile<ReviewVO>{
    
	private final AdminFileUploadManager fileUploadManager;
	private final AdminReviewMapper mapper;

	@Override
	public void register(MultipartFile[] uploadFile, ReviewVO reviewVO) {
		if (!uploadFile[0].isEmpty()) { 
			String imageURL = fileUploadManager.uploadFiles(uploadFile).get("imageURLs");
			reviewVO.setReviewImageURL(imageURL);
		}
		log.info("register... " + reviewVO);
		mapper.insertSelectKey(reviewVO);
		
	}

	@Override
	public ReviewVO get(Long reviewId) {
		log.info("get..." + reviewId);	
		return mapper.read(reviewId);
	}

	@Override
	public boolean modify(MultipartFile[] uploadFile, ReviewVO reviewVO) {
		if (!uploadFile[0].isEmpty()) { 
			String imageURL = fileUploadManager.uploadFiles(uploadFile).get("imageURLs");
			reviewVO.setReviewImageURL(imageURL);
		}
		
		LocalDateTime now = LocalDateTime.now();
		Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
		reviewVO.setReviewUpdateDate(date);
		return mapper.update(reviewVO)==1;
	}

	@Override
	public boolean remove(Long reviewId) {
		log.info("remove ... " + reviewId);
		return mapper.delete(reviewId)==1;
	}

	@Override
	public List<ReviewVO> getList(Criteria cri) {
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
