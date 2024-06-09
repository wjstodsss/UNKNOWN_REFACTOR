package com.unknown.paldak.admin.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.common.domain.PageDTO;
import com.unknown.paldak.admin.domain.ItemVO;
import com.unknown.paldak.admin.mapper.AdminItemMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class AdminItemServiceImpl implements BaseService<ItemVO>{
    
	@Autowired
	private AdminItemMapper mapper;

	@Override
	public void register(ItemVO itemVO) {
		mapper.insertSelectKey(itemVO);
	}

	@Override
	public ItemVO get(Long itemId) {

		return mapper.read(itemId);
	}

	@Override
	public boolean modify(ItemVO itemVO) {
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
	public List<ItemVO> getDescList(Criteria cri) {
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
	
	public boolean registerItemState(ItemVO itemVO) {
		int result = mapper.insertItemState(itemVO);
		return result==1;
	}

	
	public boolean modifyItemState(ItemVO itemVO) {
		int result = mapper.updateItemState(itemVO);
		return result==1;
	}
	
}
