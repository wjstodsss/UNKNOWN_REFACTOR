package com.unknown.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unknown.mapper.AdminMapper;
import com.unknown.model.AttachImageVO;
import com.unknown.model.CateVO;
import com.unknown.model.Criteria;
import com.unknown.model.ItemVO;
import com.unknown.model.OrderDTO;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper adminMapper;

	/* ��ǰ ��� */
	@Transactional /* ACID  */
	@Override
	public void itemEnroll(ItemVO item) {
		adminMapper.itemEnroll(item);
        System.out.println("Item ID after insertion: " + item.getItemId()); // ����� �α� �߰�
        if (item.getImageList() != null && !item.getImageList().isEmpty()) {
            for (AttachImageVO image : item.getImageList()) {
                image.setItemId(item.getItemId());  // �ܷ� Ű ����
                adminMapper.imageEnroll(image);
            }
        }
    }


	/* ī�װ� ����Ʈ */
	@Override
	public List<CateVO> cateList() {

		log.info("(service)cateList........");

		return adminMapper.cateList();
	}

	/* ��ǰ ����Ʈ */
	@Override
	public List<ItemVO> goodsGetList(Criteria cri) {
		log.info("goodsGetTotalList()..........");
		return adminMapper.goodsGetList(cri);
	}

	/* ��ǰ �� ���� */
	public int goodsGetTotal(Criteria cri) {
		log.info("goodsGetTotal().........");
		return adminMapper.goodsGetTotal(cri);
	}

	/* ��ǰ ��ȸ ������ */
	@Override
	public ItemVO goodsGetDetail(int itemId) {

		log.info("(service)itemGetDetail......." + itemId);

		return adminMapper.goodsGetDetail(itemId);
	}

	/* ��ǰ ���� ���� */
	@Override
	@Transactional
	public int goodsModify(ItemVO vo) {
		// ��ǰ ���� ����
				int result = adminMapper.goodsModify(vo);
				
				// ���� �̹��� ���� ����
				adminMapper.deleteImageAll(vo.getItemId());

				// ���ο� �̹��� ���� ����
				if (vo.getImageList() != null && !vo.getImageList().isEmpty()) {
					vo.getImageList().forEach(attach -> {
						attach.setItemId(vo.getItemId());
						adminMapper.imageEnroll(attach);
					});
				}

				return result;
			}

	/* ��ǰ ���� ���� */
	@Override
	@Transactional
	public int goodsDelete(int itemId) {

		log.info("goodsDelete..........");
		
		adminMapper.deleteImageAll(itemId);

		return adminMapper.goodsDelete(itemId);
	}
	
	/* ���� ��ǰ �̹��� ���� ��� */
	@Override
	public List<AttachImageVO> getAttachInfo(int itemId) {
		
		log.info("getAttachInfo........");
		
		return adminMapper.getAttachInfo(itemId);
	}
	
	/* �ֹ� ��ǰ ����Ʈ */
	@Override
	public List<OrderDTO> getOrderList(Criteria cri) {
		return adminMapper.getOrderList(cri);
	}
	
	/* �ֹ� �� ���� */
	@Override
	public int getOrderTotal(Criteria cri) {
		return adminMapper.getOrderTotal(cri);
	}

}
