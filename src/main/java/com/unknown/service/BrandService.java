package com.unknown.service;

import java.util.List;


import com.unknown.model.BrandVO;
import com.unknown.model.Criteria;


public interface BrandService {
	/* 작가 등록 */
    public void brandEnroll(BrandVO brand) throws Exception;
    
    /* 작가 목록 */
    public List<BrandVO> brandGetList(Criteria cri) throws Exception;
    
    /* 작가 총 인원 */
    public int brandGetTotal(Criteria cri) throws Exception;
    
    /* 작가 상세 페이지 */
	public BrandVO brandGetDetail(int brandId) throws Exception;
	
	/* 작가 정보 수정 */
	public int brandModify(BrandVO brand) throws Exception;
	
	/* 작가 정보 삭제 */
	public int brandDelete(int brandId);
    
}
