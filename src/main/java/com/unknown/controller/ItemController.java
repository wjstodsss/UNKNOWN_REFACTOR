package com.unknown.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.unknown.mapper.AttachMapper;
import com.unknown.model.AttachImageVO;
import com.unknown.model.Criteria;
import com.unknown.model.ItemSalesDTO;
import com.unknown.model.ItemVO;
import com.unknown.model.PageDTO;
import com.unknown.model.ReviewVO;
import com.unknown.service.ItemService;
import com.unknown.service.OrderService;
import com.unknown.service.ReviewService;

@Controller
public class ItemController {

	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

	@Autowired
	private AttachMapper attachMapper;

	@Autowired
	private ItemService itemService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ReviewService reviewService;
	
	@GetMapping("/main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String mainPageGET(Model model) {
	    logger.info("메인 페이지 진입");

	    // 예시: 특정 itemId를 지정하여 상품 정보 가져오기
	    List<Integer> itemIds = List.of(1, 2, 3, 4); // 실제 itemId 리스트를 사용
	    List<ItemVO> productList = new LinkedList<>();

	    for (Integer itemId : itemIds) {
	        ItemVO item = itemService.getGoodsInfo(itemId);
	        if (item != null) {
	            item.setImageList(attachMapper.getAttachList(itemId)); // 이미지 리스트 추가
	            productList.add(item);
	        }
	    }

	    model.addAttribute("productList", productList);

	    // 랭킹이 낮은 모든 상품 가져오기
	    List<ItemVO> bottomRankedItems = orderService.getBottomRankedItems();
	    for (ItemVO item : bottomRankedItems) {
	        item.setImageList(attachMapper.getAttachList(item.getItemId()));
	    }
	    model.addAttribute("bottomRankedItems", bottomRankedItems);

	    // 모든 랭킹 상품 가져오기
	    List<ItemSalesDTO> topSellingItems = orderService.getTopSellingItems();
	    for (ItemSalesDTO item : topSellingItems) {
	        item.setImageList(attachMapper.getAttachList(item.getItemId()));
	    }
	    model.addAttribute("topSellingItems", topSellingItems);

	    // 금주의 BEST 신상품 가져오기
	    List<ItemVO> bestNewItems = itemService.getItemsByCateCode("1000"); // 예시 카테고리 코드
	    for (ItemVO item : bestNewItems) {
	        item.setImageList(attachMapper.getAttachList(item.getItemId()));
	    }
	    model.addAttribute("bestNewItems", bestNewItems);

	    // 간편하게 먹자! 요즘 대세 쫄깃 닭다리살(=CateCode 1402) 상품 가져오기
	    List<ItemVO> cateCode1402Items = itemService.getItemsByCateCode("1402");
	    for (ItemVO item : cateCode1402Items) {
	        item.setImageList(attachMapper.getAttachList(item.getItemId()));
	    }
	    model.addAttribute("cateCode1402Items", cateCode1402Items);

	    // '패키지'가 포함된 상품 가져오기
	    List<ItemVO> packageItems = itemService.getItemsByName("패키지");
	    for (ItemVO item : packageItems) {
	        item.setImageList(attachMapper.getAttachList(item.getItemId()));
	    }
	    model.addAttribute("packageItems", packageItems);

	    // brandId가 2인 상품 가져오기
	    List<ItemVO> brandId2Items = itemService.getItemsByBrandId(2);
	    for (ItemVO item : brandId2Items) {
	        item.setImageList(attachMapper.getAttachList(item.getItemId()));
	    }
	    model.addAttribute("brandId2Items", brandId2Items);

	    return "main";
	}


	@GetMapping("/display")
	public ResponseEntity<byte[]> getImage(String fileName) {

		logger.info("getImage()....." + fileName);
		File file = new File("c:\\upload\\" + fileName);
		ResponseEntity<byte[]> result = null;

		try {

			HttpHeaders header = new HttpHeaders();

			header.add("Content-type", Files.probeContentType(file.toPath()));

			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	/* 이미지 정보 반환 */
	@GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<AttachImageVO>> getAttachList(int itemId) {

		logger.info("getAttachList.........." + itemId);

		return new ResponseEntity<List<AttachImageVO>>(attachMapper.getAttachList(itemId), HttpStatus.OK);

	}

	/* 상품 검색 */
	@GetMapping("search")
	public String searchGoodsGET(Criteria cri, Model model) {

		logger.info("cri : " + cri);

		List<ItemVO> list = itemService.getGoodsList(cri);
		logger.info("pre list : " + list);
		if (!list.isEmpty()) {
			model.addAttribute("list", list);
			logger.info("list : " + list);
		} else {
			model.addAttribute("listcheck", "empty");

			return "search";
		}

		model.addAttribute("pageMaker", new PageDTO(cri, itemService.goodsGetTotal(cri)));

		return "search";

	}

	@GetMapping("/goodsDetail/{itemId}")
	public String goodsDetailGET(@PathVariable("itemId") int itemId, @ModelAttribute("cri") Criteria cri, Model model,
			HttpSession session) {
		logger.info("goodsDetailGET()..........");

		ItemVO goodsInfo = itemService.getGoodsInfo(itemId);
		List<ReviewVO> reviewList = reviewService.getListByItemId(itemId); // itemId로 리뷰 목록 가져오기

		model.addAttribute("goodsInfo", goodsInfo);
		model.addAttribute("reviewList", reviewList); // 리뷰 목록 추가

		// 최근 본 상품 목록을 세션에 저장
		List<ItemVO> recentItems = (List<ItemVO>) session.getAttribute("recentItems");
		if (recentItems == null) {
			recentItems = new LinkedList<>();
		}

		// 이미 존재하는 아이템은 삭제
		recentItems.removeIf(i -> i.getItemId() == itemId);
		// 맨 앞에 추가
		recentItems.add(0, goodsInfo);

		// 최근 본 상품 최대 10개만 유지
		if (recentItems.size() > 10) {
			recentItems.remove(10);
		}

		session.setAttribute("recentItems", recentItems);

		return "/goodsDetail";
	}

}
