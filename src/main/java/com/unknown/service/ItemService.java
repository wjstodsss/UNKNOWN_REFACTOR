package com.unknown.service;

import java.util.List;

import com.unknown.model.ItemVO;
import com.unknown.model.Criteria;

public interface ItemService {
	
	
	
	/* ��ǰ �˻� */
	public List<ItemVO> getGoodsList(Criteria cri);
	
	/* ��ǰ �� ���� */
	public int goodsGetTotal(Criteria cri);
	
	/* ��ǰ ���� */
	public ItemVO getGoodsInfo(int itemId);
	
	/* ��� ��ǰ ����Ʈ */
    public List<ItemVO> getAllItems();
	
	/* ī�װ� ���� ��ǰ ����Ʈ */
	public List<ItemVO> getItemsByCateCode(String cateCode);
	
	/* ī�װ� ���� ���� ��ǰ ����Ʈ */
	public List<ItemVO> getItemsByCateRange(String startCode, String endCode);
	
	/* ��ǰ �̸����� ��ǰ �ҷ����� */
	public List<ItemVO> getItemsByName(String keyword);
	
	/* Ư�� �귣�� ID�� �ش��ϴ� ��ǰ �ҷ����� */
    public List<ItemVO> getItemsByBrandId(int brandId);

}
