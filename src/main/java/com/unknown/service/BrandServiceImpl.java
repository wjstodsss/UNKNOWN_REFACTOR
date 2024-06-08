package com.unknown.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unknown.mapper.BrandMapper;
import com.unknown.model.BrandVO;
import com.unknown.model.Criteria;

import lombok.extern.log4j.Log4j;


@Log4j
@Service
public class BrandServiceImpl implements BrandService {

	
	@Autowired
	BrandMapper brandMapper;

	/* �۰� ��� */
	@Override
	public void brandEnroll(BrandVO brand) throws Exception {

		brandMapper.brandEnroll(brand);

	}

	/* �۰� ��� */
	@Override
	public List<BrandVO> brandGetList(Criteria cri) throws Exception {

		return brandMapper.brandGetList(cri);
	}
	
	/* �۰� �� �� */
    @Override
    public int brandGetTotal(Criteria cri) throws Exception {
        log.info("(service)brandGetTotal()......." + cri);
        return brandMapper.brandGetTotal(cri);
    }
    
    /* �۰� �� ������ */
	@Override
	public BrandVO brandGetDetail(int brandId) throws Exception {
		log.info("brandGetDetail........" + brandId);
		return brandMapper.brandGetDetail(brandId);
	}
	
	/* �۰� ���� ���� */
	@Override
	public int brandModify(BrandVO brand) throws Exception {
		log.info("(service) brandModify........." + brand);
		return brandMapper.brandModify(brand);
	}
	
	/* �۰� ���� ���� */
	@Override
	public int brandDelete(int brandId) {
		
		log.info("brandDelete..........");
		
		return brandMapper.brandDelete(brandId);
	}

}
