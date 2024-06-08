package com.unknown.mapper;

import java.util.List;

import com.unknown.model.BrandVO;
import com.unknown.model.Criteria;

public interface BrandMapper {

	/* 브랜드 등록 */
	public void brandEnroll(BrandVO brand);

	/* 브랜드 목록 */
	public List<BrandVO> brandGetList(Criteria cri);

	/* 브랜드 총 수 */
	public int brandGetTotal(Criteria cri);

	/* 브랜드 상세 */
	public BrandVO brandGetDetail(int brandId);

	/* 브랜드 정보 수정 */
	public int brandModify(BrandVO brand);
	
	/* 브랜드 정보 삭제 */
	public int brandDelete(int brandId);

}
