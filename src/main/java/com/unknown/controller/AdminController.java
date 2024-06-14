package com.unknown.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unknown.model.AttachImageVO;
import com.unknown.model.BrandVO;
import com.unknown.model.CateVO;
import com.unknown.model.Criteria;
import com.unknown.model.ItemVO;
import com.unknown.model.OrderCancelDTO;
import com.unknown.model.OrderDTO;
import com.unknown.model.PageDTO;
import com.unknown.service.AdminService;
import com.unknown.service.BrandService;
import com.unknown.service.OrderService;

import net.coobird.thumbnailator.Thumbnails;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private BrandService brandService;

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private OrderService orderService;

	/* 관리자 메인 페이지 이동 */
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public void adminMainGET() throws Exception {

		logger.info("관리자 페이지 이동");

	}

	/* 상품 등록 페이지 접속 */
	@RequestMapping(value = "goodsEnroll", method = RequestMethod.GET)
	public void goodsEnrollGET(Model model) throws Exception {
		logger.info("상품 등록 페이지 접속");

	    ObjectMapper objm = new ObjectMapper();

	    List<CateVO> list = adminService.cateList(); // CateVO로 캐스팅

	    String cateList = objm.writeValueAsString(list);

	    model.addAttribute("cateList", cateList);


	}

	/* 상품 관리 페이지 접속 */
	@RequestMapping(value = "goodsManage", method = RequestMethod.GET)
	public void goodsManageGET(Criteria cri, Model model) throws Exception {
		logger.info("상품 관리 페이지 접속");

		/* 상품 리스트 데이터 */
		List list = adminService.goodsGetList(cri);

		if (!list.isEmpty()) {
			model.addAttribute("list", list);
		} else {
			model.addAttribute("listCheck", "empty");
			return;
		}

		/* 페이지 인터페이스 데이터 */
		model.addAttribute("pageMaker", new PageDTO(cri, adminService.goodsGetTotal(cri)));
	}

	/* 브랜드 등록 페이지 접속 */
	@RequestMapping(value = "brandEnroll", method = RequestMethod.GET)
	public void brandEnrollGET() throws Exception {
		logger.info("브랜드 등록 페이지 접속");
	}

	/* 브랜드 관리 페이지 접속 */
	@RequestMapping(value = "brandManage", method = RequestMethod.GET)
	public String brandManageGET(Criteria cri, Model model) throws Exception {
		logger.info("브랜드 관리 페이지 접속.........." + cri);

		/* 브랜드 목록 출력 데이터 */
		List<BrandVO> list = brandService.brandGetList(cri);

		if (!list.isEmpty()) {
			model.addAttribute("list", list); // 브랜드 존재 경우
		} else {
			model.addAttribute("listCheck", "empty"); // 브랜드 존재하지 않을 경우
		}

		/* 페이지 이동 인터페이스 데이터 */
		int total = brandService.brandGetTotal(cri);

		PageDTO pageMaker = new PageDTO(cri, total);

		model.addAttribute("pageMaker", pageMaker);

		return "admin/brandManage"; // 브랜드 관리 페이지의 JSP 파일 경로를 반환
	}

	@RequestMapping(value = "brandEnroll.do", method = RequestMethod.POST)
	public String brandEnrollPOST(BrandVO brand, RedirectAttributes rttr) throws Exception {

		logger.info("brandEnroll :" + brand);

		brandService.brandEnroll(brand); // 브랜드 등록 쿼리 수행

		rttr.addFlashAttribute("enroll_result", brand.getBrandName());

		return "redirect:/admin/brandManage";

	}

	/* 브랜드 상세, 수정 페이지 */
	@GetMapping({ "/brandDetail", "/brandModify" })
	public void brandGetInfoGET(Integer brandId, Criteria cri, Model model) throws Exception {

		logger.info("brandDetail......." + brandId);

		/* 브랜드 관리 페이지 정보 */
		model.addAttribute("cri", cri);

		/* 선택 브랜드 정보 */
		model.addAttribute("brandInfo", brandService.brandGetDetail(brandId));

	}

	/* 브랜드 정보 수정 */
	@PostMapping("/brandModify")
	public String brandModifyPOST(BrandVO brand, RedirectAttributes rttr) throws Exception {
		logger.info("brandModifyPOST......." + brand);

		int result = brandService.brandModify(brand);

		rttr.addFlashAttribute("modify_result", result);

		return "redirect:/admin/brandManage";
	}

	/* 상품 등록 */
	@PostMapping("/goodsEnroll")
	public String goodsEnrollPOST(ItemVO item, RedirectAttributes rttr) {

		adminService.itemEnroll(item);
		rttr.addFlashAttribute("enroll_result", item.getItemName());
		return "redirect:/admin/goodsManage";
	}

	/* 브랜드 검색 팝업창 */
	@GetMapping("/brandPop")
	public void brandPopGET(Criteria cri, Model model) throws Exception {

		logger.info("brandPopGET.......");

		cri.setAmount(5);

		/* 게시물 출력 데이터 */
		List list = brandService.brandGetList(cri);

		if (!list.isEmpty()) {
			model.addAttribute("list", list); // 브랜드 존재 경우
		} else {
			model.addAttribute("listCheck", "empty"); // 브랜드 존재하지 않을 경우
		}

		/* 페이지 이동 인터페이스 데이터 */
		model.addAttribute("pageMaker", new PageDTO(cri, brandService.brandGetTotal(cri)));

	}

	/* 상품 조회 페이지 */
	@GetMapping({ "/goodsDetail", "/goodsModify" })
	public void goodsGetInfoGET(int itemId, Criteria cri, Model model) throws JsonProcessingException {

		logger.info("goodsGetInfo()........." + itemId);

		ObjectMapper mapper = new ObjectMapper();

		/* 카테고리 리스트 데이터 */
		model.addAttribute("cateList", mapper.writeValueAsString(adminService.cateList()));

		/* 목록 페이지 조건 정보 */
		model.addAttribute("cri", cri);

		/* 조회 페이지 정보 */
		model.addAttribute("goodsInfo", adminService.goodsGetDetail(itemId));

	}

	/* 상품 정보 수정 */
	@PostMapping("/goodsModify")
    public String goodsModifyPOST(ItemVO vo, RedirectAttributes rttr) {

        logger.info("goodsModifyPOST.........." + vo);

        // 상품 정보 수정
        int result = adminService.goodsModify(vo);

        rttr.addFlashAttribute("modify_result", result);

        return "redirect:/admin/goodsManage";
    }

	/* 상품 정보 삭제 */
	@PostMapping("/goodsDelete")
	public String goodsDeletePOST(int itemId, RedirectAttributes rttr) {

		logger.info("goodsDeletePOST..........");

		List<AttachImageVO> fileList = adminService.getAttachInfo(itemId);

		if (fileList != null) {

			List<Path> pathList = new ArrayList();

			fileList.forEach(vo -> {

				// 원본 이미지
				Path path = Paths.get("C:\\upload", vo.getUploadPath(), vo.getUuid() + "_" + vo.getFileName());
				pathList.add(path);

				// 섬네일 이미지
				path = Paths.get("C:\\upload", vo.getUploadPath(), "t_" + vo.getUuid() + "_" + vo.getFileName());
				pathList.add(path);

			});

			pathList.forEach(path -> {
				path.toFile().delete();
			});

		}

		int result = adminService.goodsDelete(itemId);

		rttr.addFlashAttribute("delete_result", result);

		return "redirect:/admin/goodsManage";

	}

	/* 브랜드 정보 삭제 */
	@PostMapping("/brandDelete")
	public String brandDeletePOST(int brandId, RedirectAttributes rttr) {

		logger.info("brandDeletePOST..........");

		int result = 0;

		try {

			result = brandService.brandDelete(brandId);

		} catch (Exception e) {

			e.printStackTrace();
			result = 2;
			rttr.addFlashAttribute("delete_result", result);

			return "redirect:/admin/brandManage";

		}

		rttr.addFlashAttribute("delete_result", result);

		return "redirect:/admin/brandManage";

	}

	/* 첨부 파일 업로드 */
	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AttachImageVO>> uploadAjaxActionPOST(MultipartFile[] uploadFile) {
	    logger.info("uploadAjaxActionPOST..........");

	    /* 이미지 파일 체크 */
	    for (MultipartFile multipartFile : uploadFile) {
	        File checkfile = new File(multipartFile.getOriginalFilename());
	        String type = null;

	        try {
	            type = Files.probeContentType(checkfile.toPath());
	            logger.info("MIME TYPE : " + type);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        if (!type.startsWith("image")) {
	            List<AttachImageVO> list = null;
	            return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
	        }
	    } // for

	    String uploadFolder = "C:\\upload";

	    /* 날짜 폴더 경로 */
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = new Date();
	    String str = sdf.format(date);
	    String datePath = str.replace("-", File.separator);

	    /* 폴더 생성 */
	    File uploadPath = new File(uploadFolder, datePath);
	    if (uploadPath.exists() == false) {
	        uploadPath.mkdirs();
	    }

	    /* 이미지 정보 담는 객체 */
	    List<AttachImageVO> list = new ArrayList<>();

	    for (MultipartFile multipartFile : uploadFile) {
	        /* 이미지 정보 객체 */
	        AttachImageVO vo = new AttachImageVO();
	        /* 파일 이름 */
	        String uploadFileName = multipartFile.getOriginalFilename();
	        vo.setFileName(uploadFileName);
	        vo.setUploadPath(datePath);

	        /* uuid 적용 파일 이름 */
	        String uuid = UUID.randomUUID().toString();
	        vo.setUuid(uuid);
	        uploadFileName = uuid + "_" + uploadFileName;

	        /* 파일 위치, 파일 이름을 합친 File 객체 */
	        File saveFile = new File(uploadPath, uploadFileName);

	        /* 파일 저장 */
	        try {
	            multipartFile.transferTo(saveFile);

	            /* 썸네일 생성 방법 2 */
	            File thumbnailFile = new File(uploadPath, "t_" + uploadFileName);

	            BufferedImage bo_image = ImageIO.read(saveFile);
	            double ratio = 3;
	            int width = (int) (bo_image.getWidth() / ratio);
	            int height = (int) (bo_image.getHeight() / ratio);

	            Thumbnails.of(saveFile).size(width, height).toFile(thumbnailFile);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        list.add(vo);
	    }

	    return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/* 이미지 파일 삭제 */
	@PostMapping("/deleteFile")
	public ResponseEntity<String> deleteFile(String fileName) {

		logger.info("deleteFile........" + fileName);

		File file = null;

		try {
			/* 썸네일 파일 삭제 */
			file = new File("c:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));

			file.delete();

			/* 원본 파일 삭제 */
			String originFileName = file.getAbsolutePath().replace("s_", "");

			logger.info("originFileName : " + originFileName);

			file = new File(originFileName);

			file.delete();

		} catch (Exception e) {

			e.printStackTrace();

			return new ResponseEntity<String>("fail", HttpStatus.NOT_IMPLEMENTED);

		}
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

	/* 주문 현황 페이지 */
	@GetMapping("/orderList")
	public String orderListGET(Criteria cri, Model model) {

		List<OrderDTO> list = adminService.getOrderList(cri);

		if (!list.isEmpty()) {
			model.addAttribute("list", list);
			model.addAttribute("pageMaker", new PageDTO(cri, adminService.getOrderTotal(cri)));
		} else {
			model.addAttribute("listCheck", "empty");
		}

		return "/admin/orderList";
	}
	
	//* 주문삭제 */
	@PostMapping("/orderCancle")
	public String orderCanclePOST(OrderCancelDTO dto) {
		
		orderService.orderCancle(dto);
		
		return "redirect:/admin/orderList?keyword=" + dto.getKeyword() + "&amount=" + dto.getAmount() + "&pageNum=" + dto.getPageNum();
	}

}
