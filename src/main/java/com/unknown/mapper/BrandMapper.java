package com.unknown.mapper;

import java.util.List;

import com.unknown.model.BrandVO;
import com.unknown.model.Criteria;

public interface BrandMapper {

	/* �귣�� ��� */
	public void brandEnroll(BrandVO brand);

	/* �귣�� ��� */
	public List<BrandVO> brandGetList(Criteria cri);

	/* �귣�� �� �� */
	public int brandGetTotal(Criteria cri);

	/* �귣�� �� */
	public BrandVO brandGetDetail(int brandId);

	/* �귣�� ���� ���� */
	public int brandModify(BrandVO brand);
	
	/* �귣�� ���� ���� */
	public int brandDelete(int brandId);

}
