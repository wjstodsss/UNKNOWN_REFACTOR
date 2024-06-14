package com.unknown.service;

import java.util.List;

import com.unknown.model.AttachImageVO;
import com.unknown.model.ItemVO;
import com.unknown.model.CateVO;
import com.unknown.model.Criteria;
import com.unknown.model.OrderDTO;

public interface AdminService {
    /* ��ǰ ��� */
    public void itemEnroll(ItemVO item);    
    
    /* ī�װ� ����Ʈ */
    public List<CateVO> cateList();
    
    /* ��ǰ ����Ʈ */
    public List<ItemVO> goodsGetList(Criteria cri);
    
    /* ��ǰ �� ���� */
    public int goodsGetTotal(Criteria cri);   
    
    /* ��ǰ ��ȸ ������ */
    public ItemVO goodsGetDetail(int itemId);
    
    /* ��ǰ ���� */
    public int goodsModify(ItemVO vo);    
    
    /* ��ǰ ���� ���� */
	public int goodsDelete(int itemId);
	
	/* ���� ��ǰ �̹��� ���� ��� */
	public List<AttachImageVO> getAttachInfo(int itemId);
	
	/* �ֹ� ��ǰ ����Ʈ */
	public List<OrderDTO> getOrderList(Criteria cri);
	
	/* �ֹ� �� ���� */
	public int getOrderTotal(Criteria cri);
}
