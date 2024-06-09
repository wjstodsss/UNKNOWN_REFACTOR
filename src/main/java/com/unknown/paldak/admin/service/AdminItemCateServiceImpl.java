package com.unknown.paldak.admin.service;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.common.domain.PageDTO;
import com.unknown.paldak.admin.domain.ItemCateVO;
import com.unknown.paldak.admin.mapper.AdminItemCateMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class AdminItemCateServiceImpl implements BaseService<ItemCateVO>{
    
	@Autowired
	private AdminItemCateMapper mapper;
	

	@Override
	public void register(ItemCateVO itemCateVO) {
		mapper.insertSelectKey(itemCateVO);
	}

	@Override
	public ItemCateVO get(Long cateCode) {
		return mapper.read(cateCode);
	}

	
	@Override
	public boolean modify(ItemCateVO itemCateVO) {
		return mapper.update(itemCateVO)==1;
	}

	@Override
	public boolean remove(Long cateCode) {
		return mapper.delete(cateCode)==1;
	}

	@Override
	public List<ItemCateVO> getList(Criteria cri) {
		return mapper.getListWithPaging(cri);
	}
	
	@Override
	public List<ItemCateVO> getDescList(Criteria cri) {
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
	
	public List<ItemCateVO> getListByCateParent(Criteria cri, String cateCode) {
		return mapper.getListByCateParent(cri, cateCode);
	}
	
	public int getTotalByCateParent(Criteria cri, String cateCode) {
		return mapper.getTotalByCateParent(cri, cateCode);
	}
	
	public ItemCateVO getByStringId(String cateCode) {
		ItemCateVO item = mapper.readByStringId(cateCode);
		System.out.println(item + "ffffffffffffffffffffffffffffff");
		return item;
	}
	
	public List<ItemCateVO> getList() {
		return mapper.getList();
	}
	
	public boolean removeByStringId(String cateCode) {
	    try {
	        return mapper.deleteByStringId(cateCode) == 1;
	    } catch (Exception e) {
	        throw new RuntimeException("Error occurred while deleting the category", e);
	    }
	}


	
}