package com.unknown.service;

import java.util.List;


import com.unknown.model.BrandVO;
import com.unknown.model.Criteria;


public interface BrandService {
	/* �۰� ��� */
    public void brandEnroll(BrandVO brand) throws Exception;
    
    /* �۰� ��� */
    public List<BrandVO> brandGetList(Criteria cri) throws Exception;
    
    /* �۰� �� �ο� */
    public int brandGetTotal(Criteria cri) throws Exception;
    
    /* �۰� �� ������ */
	public BrandVO brandGetDetail(int brandId) throws Exception;
	
	/* �۰� ���� ���� */
	public int brandModify(BrandVO brand) throws Exception;
	
	/* �۰� ���� ���� */
	public int brandDelete(int brandId);
    
}
