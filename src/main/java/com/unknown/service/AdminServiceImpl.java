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

	/* 상품 등록 */
	@Transactional /* ACID  */
	@Override
	public void itemEnroll(ItemVO item) {
		adminMapper.itemEnroll(item);
        System.out.println("Item ID after insertion: " + item.getItemId()); // 디버깅 로그 추가
        if (item.getImageList() != null && !item.getImageList().isEmpty()) {
            for (AttachImageVO image : item.getImageList()) {
                image.setItemId(item.getItemId());  // 외래 키 설정
                adminMapper.imageEnroll(image);
            }
        }
    }


	/* 카테고리 리스트 */
	@Override
	public List<CateVO> cateList() {

		log.info("(service)cateList........");

		return adminMapper.cateList();
	}

	/* 상품 리스트 */
	@Override
	public List<ItemVO> goodsGetList(Criteria cri) {
		log.info("goodsGetTotalList()..........");
		return adminMapper.goodsGetList(cri);
	}

	/* 상품 총 갯수 */
	public int goodsGetTotal(Criteria cri) {
		log.info("goodsGetTotal().........");
		return adminMapper.goodsGetTotal(cri);
	}

	/* 상품 조회 페이지 */
	@Override
	public ItemVO goodsGetDetail(int itemId) {

		log.info("(service)itemGetDetail......." + itemId);

		return adminMapper.goodsGetDetail(itemId);
	}

	/* 상품 정보 수정 */
	@Override
	@Transactional
	public int goodsModify(ItemVO vo) {
		// 상품 정보 수정
				int result = adminMapper.goodsModify(vo);
				
				// 기존 이미지 정보 삭제
				adminMapper.deleteImageAll(vo.getItemId());

				// 새로운 이미지 정보 저장
				if (vo.getImageList() != null && !vo.getImageList().isEmpty()) {
					vo.getImageList().forEach(attach -> {
						attach.setItemId(vo.getItemId());
						adminMapper.imageEnroll(attach);
					});
				}

				return result;
			}

	/* 상품 정보 삭제 */
	@Override
	@Transactional
	public int goodsDelete(int itemId) {

		log.info("goodsDelete..........");
		
		adminMapper.deleteImageAll(itemId);

		return adminMapper.goodsDelete(itemId);
	}
	
	/* 지정 상품 이미지 정보 얻기 */
	@Override
	public List<AttachImageVO> getAttachInfo(int itemId) {
		
		log.info("getAttachInfo........");
		
		return adminMapper.getAttachInfo(itemId);
	}
	
	/* 주문 상품 리스트 */
	@Override
	public List<OrderDTO> getOrderList(Criteria cri) {
		return adminMapper.getOrderList(cri);
	}
	
	/* 주문 총 갯수 */
	@Override
	public int getOrderTotal(Criteria cri) {
		return adminMapper.getOrderTotal(cri);
	}

}
