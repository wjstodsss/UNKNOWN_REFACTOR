package com.unknown.paldak.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.domain.ItemCateVO;

public interface AdminItemCateMapper extends BaseMapper<ItemCateVO> {
	public ItemCateVO readByStringId(String cateCode);
	public int deleteByStringId(String cateCode);
	List<ItemCateVO> getListByCateParent(@Param("cri") Criteria cri, @Param("cateCode") String cateCode);
    int getTotalByCateParent(@Param("cri") Criteria cri, @Param("cateCode") String cateCode);
}