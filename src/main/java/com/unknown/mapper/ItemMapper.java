package com.unknown.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.unknown.model.Criteria;
import com.unknown.model.ItemVO;

public interface ItemMapper {

	/* ��ǰ �˻� */
	public List<ItemVO> getGoodsList(Criteria cri);

	/* ��ǰ �� ���� */
	public int goodsGetTotal(Criteria cri);

	/* �귣�� id ����Ʈ ��û */
	public String[] getBrandIdList(String keyword);

	/* ��ǰ ���� */
	public ItemVO getGoodsInfo(int itemId);

	/* ��� ��ǰ ����Ʈ */
	public List<ItemVO> getAllItems();

	/* ī�װ� ��ȣ�� ��ǰ ����Ʈ ��û */
	public List<ItemVO> getItemsByCateCode(String cateCode);

	/* ī�װ� ��ȣ ������ ��ǰ ����Ʈ ��û */
	public List<ItemVO> getItemsByCateRange(@Param("startCode") String startCode, @Param("endCode") String endCode);

	/* �ְ� �Ǹŷ� ��ǰ ����Ʈ */
	public List<ItemVO> getTopSellingItems();

	/* ���� �Ǹŷ� ��ǰ ����Ʈ */
	public List<ItemVO> getBottomRankedItems();

	// Ư�� �ܾ ���Ե� ��ǰ �˻�
	public List<ItemVO> getItemsByName(@Param("keyword") String keyword);
	
	// Ư�� �귣�� ID�� �ش��ϴ� ��ǰ �˻�
    public List<ItemVO> getItemsByBrandId(@Param("brandId") int brandId);

}
