package com.unknown.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unknown.mapper.AdminMapper;
import com.unknown.mapper.AttachMapper;
import com.unknown.mapper.ItemMapper;
import com.unknown.model.AttachImageVO;
import com.unknown.model.Criteria;
import com.unknown.model.ItemVO;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private AttachMapper attachMapper;
    
    @Autowired
    private AdminMapper adminMapper;

    /* 상품 검색 */
    @Override
    public List<ItemVO> getGoodsList(Criteria cri) {
        log.info("getGoodsList().......");

        String type = cri.getType();
        String[] typeArr = type.split("");
        String[] brandArr = itemMapper.getBrandIdList(cri.getKeyword());

        if (type.equals("A") || type.equals("AC") || type.equals("AT") || type.equals("ACT")) {
            if (brandArr.length == 0) {
                return new ArrayList<>();
            }
        }

        for (String t : typeArr) {
            if (t.equals("A")) {
                cri.setBrandArr(brandArr);
            }
        }

        List<ItemVO> list = itemMapper.getGoodsList(cri);

        list.forEach(item -> {
            int itemId = item.getItemId();
            List<AttachImageVO> imageList = attachMapper.getAttachList(itemId);
            item.setImageList(imageList);
        });

        return list;
    }

    /* 상품 총 갯수 */
    @Override
    public int goodsGetTotal(Criteria cri) {
        log.info("goodsGetTotal().......");
        return itemMapper.goodsGetTotal(cri);
    }

    /* 상품 정보 */
    @Override
    public ItemVO getGoodsInfo(int itemId) {
        ItemVO goodsInfo = itemMapper.getGoodsInfo(itemId);
        goodsInfo.setImageList(adminMapper.getAttachInfo(itemId));
        return goodsInfo;
    }
    
    @Override
    public List<ItemVO> getAllItems() {
        List<ItemVO> items = itemMapper.getAllItems();
        for (ItemVO item : items) {
            List<AttachImageVO> imageList = attachMapper.getAttachList(item.getItemId());
            item.setImageList(imageList);
        }
        return items;
    }

    /* 카테고리 기준 상품 리스트 */
    @Override
    public List<ItemVO> getItemsByCateCode(String cateCode) {
        List<ItemVO> items = itemMapper.getItemsByCateCode(cateCode);
        for (ItemVO item : items) {
            List<AttachImageVO> imageList = attachMapper.getAttachList(item.getItemId());
            item.setImageList(imageList);
        }
        return items;
    }
    
    /* 카테고리 범위 기준 상품 리스트 */
    @Override
    public List<ItemVO> getItemsByCateRange(String startCode, String endCode) {
        List<ItemVO> items = itemMapper.getItemsByCateRange(startCode, endCode);
        for (ItemVO item : items) {
            List<AttachImageVO> imageList = attachMapper.getAttachList(item.getItemId());
            item.setImageList(imageList);
        }
        return items;
    }
    
    /* 상품 이름으로 상품 불러오기 */
    @Override
    public List<ItemVO> getItemsByName(String keyword) {
        List<ItemVO> items = itemMapper.getItemsByName(keyword);
        for (ItemVO item : items) {
            item.setImageList(attachMapper.getAttachList(item.getItemId()));
        }
        return items;
    }
    
    /* 특정 브랜드 ID에 해당하는 상품 불러오기 */
    @Override
    public List<ItemVO> getItemsByBrandId(int brandId) {
        List<ItemVO> items = itemMapper.getItemsByBrandId(brandId);
        for (ItemVO item : items) {
            item.setImageList(attachMapper.getAttachList(item.getItemId()));
        }
        return items;
    }
    
}
