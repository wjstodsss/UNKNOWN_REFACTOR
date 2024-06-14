package com.unknown.paldak.admin.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.common.domain.PageDTO;
import com.unknown.paldak.admin.domain.AttachImageVO;
import com.unknown.paldak.admin.domain.ItemVO;
import com.unknown.paldak.admin.mapper.AdminItemMapper;
import com.unknown.paldak.admin.util.AdminFileUploadManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@RequiredArgsConstructor
public class AdminItemServiceImpl implements BaseServiceWithFile<ItemVO>{
    
	private final AdminFileUploadManager fileUploadManager;
	private final AdminItemMapper mapper;
    private final AdminAttachServiceImpl attachService;

	@Override
	public void register(MultipartFile[] uploadFile, ItemVO itemVO) {
		AttachImageVO attachImageVO = new AttachImageVO();
		
		if (!uploadFile[0].isEmpty()) { 
			String imageURL = fileUploadManager.uploadFiles(uploadFile).get("imageURLs");
			itemVO.setItemImageURL(imageURL);
		}
		
		mapper.insertSelectKey(itemVO);
		
		long newId = itemVO.getItemId();
		
		if (!uploadFile[0].isEmpty()) { 		
			Map<String, String> imageInfo = fileUploadManager.uploadFiles(uploadFile);
			String uuid = imageInfo.get("uuid");
			String fileName = imageInfo.get("originalFilename");
			String uploadPath = imageInfo.get("datePath");
			attachImageVO.setUuid(uuid);
			attachImageVO.setFileName(fileName);
			attachImageVO.setUploadPath(uploadPath);
			attachImageVO.setItemId(newId);
	        attachService.register(attachImageVO);
		}
		
	}

	@Override
	public ItemVO get(Long itemId) {

		return mapper.read(itemId);
	}

	@Override
	public boolean modify(MultipartFile[] uploadFile, ItemVO itemVO) {
		AttachImageVO attachImageVO = new AttachImageVO();

		long currentItemId = itemVO.getItemId();
        
        if (!uploadFile[0].isEmpty()) { 		
			Map<String, String> imageInfo = fileUploadManager.uploadFiles(uploadFile);
			String uuid = imageInfo.get("uuid");
			String fileName = imageInfo.get("originalFilename");
			String uploadPath = imageInfo.get("datePath");
			attachImageVO.setUuid(uuid);
			attachImageVO.setFileName(fileName);
			attachImageVO.setUploadPath(uploadPath);
			attachImageVO.setItemId(currentItemId);
	        attachService.modify(attachImageVO);		     
		}
        
       
		if (!uploadFile[0].isEmpty()) { 
			String imageURL = fileUploadManager.uploadFiles(uploadFile).get("imageURLs");
			itemVO.setItemImageURL(imageURL);
		}
		
		LocalDateTime now = LocalDateTime.now();
		Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
		itemVO.setUpdateDate(date);
		
		return mapper.update(itemVO)==1;
	}

	@Override
	public boolean remove(Long itemId) {
		log.info("remove ... " + itemId);
		return mapper.delete(itemId)==1;
	}

	@Override
	public List<ItemVO> getList(Criteria cri) {
		List<ItemVO> result = mapper.getListWithPaging(cri);
		result.forEach(itemVO -> log.info(itemVO));
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
	
	public boolean registerItemState(ItemVO itemVO) {
		int result = mapper.insertItemState(itemVO);
		return result==1;
	}

	
	public boolean modifyItemState(ItemVO itemVO) {
		int result = mapper.updateItemState(itemVO);
		return result==1;
	}
	
}
