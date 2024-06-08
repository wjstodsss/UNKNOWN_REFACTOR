package com.unknown.mapper;

import java.util.List;

import com.unknown.model.CartDTO;

public interface CartMapper {
	
	/* īƮ �߰� */
	public int addCart(CartDTO cart) throws Exception;
	
	/* īƮ ���� */
	public int deleteCart(int cartId);
	
	/* īƮ ���� ���� */
	public int modifyCount(CartDTO cart);
	
	/* īƮ ��� */
	public List<CartDTO> getCart(String memberId);	
	
	/* īƮ Ȯ�� */
	public CartDTO checkCart(CartDTO cart);
	
	/* īƮ �� ��ǰ ����(�ֹ�) */
	public int deleteOrderCart(CartDTO dto);

}
