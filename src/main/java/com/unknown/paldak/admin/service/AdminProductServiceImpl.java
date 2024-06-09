package com.unknown.paldak.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.common.domain.PageDTO;
import com.unknown.paldak.admin.domain.NoticeVO;
import com.unknown.paldak.admin.domain.ProductVO;
import com.unknown.paldak.admin.mapper.AdminProductMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class AdminProductServiceImpl implements BaseService<ProductVO>{
    
	@Autowired
	private AdminProductMapper mapper;

	@Override
	public void register(ProductVO productVO) {
		log.info("register... " + productVO);
		mapper.insertSelectKey(productVO);
		
	}

	@Override
	public ProductVO get(Long productId) {
		log.info("get..." + productId);	
		return mapper.read(productId);
	}

	@Override
	public boolean modify(ProductVO productVO) {
		return mapper.update(productVO)==1;
	}

	@Override
	public boolean remove(Long productId) {
		log.info("remove ... " + productId);
		return mapper.delete(productId)==1;
	}

	@Override
	public List<ProductVO> getList(Criteria cri) {
		System.out.println(cri);
		return mapper.getListWithPaging(cri);
	}
	
	@Override
	public List<ProductVO> getDescList(Criteria cri) {
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
