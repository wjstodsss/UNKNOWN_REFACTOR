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

	/* ������ ���� ������ �̵� */
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public void adminMainGET() throws Exception {

		logger.info("������ ������ �̵�");

	}

	/* ��ǰ ��� ������ ���� */
	@RequestMapping(value = "goodsEnroll", method = RequestMethod.GET)
	public void goodsEnrollGET(Model model) throws Exception {
		logger.info("��ǰ ��� ������ ����");

	    ObjectMapper objm = new ObjectMapper();

	    List<CateVO> list = adminService.cateList(); // CateVO�� ĳ����

	    String cateList = objm.writeValueAsString(list);

	    model.addAttribute("cateList", cateList);


	}

	/* ��ǰ ���� ������ ���� */
	@RequestMapping(value = "goodsManage", method = RequestMethod.GET)
	public void goodsManageGET(Criteria cri, Model model) throws Exception {
		logger.info("��ǰ ���� ������ ����");

		/* ��ǰ ����Ʈ ������ */
		List list = adminService.goodsGetList(cri);

		if (!list.isEmpty()) {
			model.addAttribute("list", list);
		} else {
			model.addAttribute("listCheck", "empty");
			return;
		}

		/* ������ �������̽� ������ */
		model.addAttribute("pageMaker", new PageDTO(cri, adminService.goodsGetTotal(cri)));
	}

	/* �귣�� ��� ������ ���� */
	@RequestMapping(value = "brandEnroll", method = RequestMethod.GET)
	public void brandEnrollGET() throws Exception {
		logger.info("�귣�� ��� ������ ����");
	}

	/* �귣�� ���� ������ ���� */
	@RequestMapping(value = "brandManage", method = RequestMethod.GET)
	public String brandManageGET(Criteria cri, Model model) throws Exception {
		logger.info("�귣�� ���� ������ ����.........." + cri);

		/* �귣�� ��� ��� ������ */
		List<BrandVO> list = brandService.brandGetList(cri);

		if (!list.isEmpty()) {
			model.addAttribute("list", list); // �귣�� ���� ���
		} else {
			model.addAttribute("listCheck", "empty"); // �귣�� �������� ���� ���
		}

		/* ������ �̵� �������̽� ������ */
		int total = brandService.brandGetTotal(cri);

		PageDTO pageMaker = new PageDTO(cri, total);

		model.addAttribute("pageMaker", pageMaker);

		return "admin/brandManage"; // �귣�� ���� �������� JSP ���� ��θ� ��ȯ
	}

	@RequestMapping(value = "brandEnroll.do", method = RequestMethod.POST)
	public String brandEnrollPOST(BrandVO brand, RedirectAttributes rttr) throws Exception {

		logger.info("brandEnroll :" + brand);

		brandService.brandEnroll(brand); // �귣�� ��� ���� ����

		rttr.addFlashAttribute("enroll_result", brand.getBrandName());

		return "redirect:/admin/brandManage";

	}

	/* �귣�� ��, ���� ������ */
	@GetMapping({ "/brandDetail", "/brandModify" })
	public void brandGetInfoGET(Integer brandId, Criteria cri, Model model) throws Exception {

		logger.info("brandDetail......." + brandId);

		/* �귣�� ���� ������ ���� */
		model.addAttribute("cri", cri);

		/* ���� �귣�� ���� */
		model.addAttribute("brandInfo", brandService.brandGetDetail(brandId));

	}

	/* �귣�� ���� ���� */
	@PostMapping("/brandModify")
	public String brandModifyPOST(BrandVO brand, RedirectAttributes rttr) throws Exception {
		logger.info("brandModifyPOST......." + brand);

		int result = brandService.brandModify(brand);

		rttr.addFlashAttribute("modify_result", result);

		return "redirect:/admin/brandManage";
	}

	/* ��ǰ ��� */
	@PostMapping("/goodsEnroll")
	public String goodsEnrollPOST(ItemVO item, RedirectAttributes rttr) {

		adminService.itemEnroll(item);
		rttr.addFlashAttribute("enroll_result", item.getItemName());
		return "redirect:/admin/goodsManage";
	}

	/* �귣�� �˻� �˾�â */
	@GetMapping("/brandPop")
	public void brandPopGET(Criteria cri, Model model) throws Exception {

		logger.info("brandPopGET.......");

		cri.setAmount(5);

		/* �Խù� ��� ������ */
		List list = brandService.brandGetList(cri);

		if (!list.isEmpty()) {
			model.addAttribute("list", list); // �귣�� ���� ���
		} else {
			model.addAttribute("listCheck", "empty"); // �귣�� �������� ���� ���
		}

		/* ������ �̵� �������̽� ������ */
		model.addAttribute("pageMaker", new PageDTO(cri, brandService.brandGetTotal(cri)));

	}

	/* ��ǰ ��ȸ ������ */
	@GetMapping({ "/goodsDetail", "/goodsModify" })
	public void goodsGetInfoGET(int itemId, Criteria cri, Model model) throws JsonProcessingException {

		logger.info("goodsGetInfo()........." + itemId);

		ObjectMapper mapper = new ObjectMapper();

		/* ī�װ� ����Ʈ ������ */
		model.addAttribute("cateList", mapper.writeValueAsString(adminService.cateList()));

		/* ��� ������ ���� ���� */
		model.addAttribute("cri", cri);

		/* ��ȸ ������ ���� */
		model.addAttribute("goodsInfo", adminService.goodsGetDetail(itemId));

	}

	/* ��ǰ ���� ���� */
	@PostMapping("/goodsModify")
    public String goodsModifyPOST(ItemVO vo, RedirectAttributes rttr) {

        logger.info("goodsModifyPOST.........." + vo);

        // ��ǰ ���� ����
        int result = adminService.goodsModify(vo);

        rttr.addFlashAttribute("modify_result", result);

        return "redirect:/admin/goodsManage";
    }

	/* ��ǰ ���� ���� */
	@PostMapping("/goodsDelete")
	public String goodsDeletePOST(int itemId, RedirectAttributes rttr) {

		logger.info("goodsDeletePOST..........");

		List<AttachImageVO> fileList = adminService.getAttachInfo(itemId);

		if (fileList != null) {

			List<Path> pathList = new ArrayList();

			fileList.forEach(vo -> {

				// ���� �̹���
				Path path = Paths.get("C:\\upload", vo.getUploadPath(), vo.getUuid() + "_" + vo.getFileName());
				pathList.add(path);

				// ������ �̹���
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

	/* �귣�� ���� ���� */
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

	/* ÷�� ���� ���ε� */
	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AttachImageVO>> uploadAjaxActionPOST(MultipartFile[] uploadFile) {
	    logger.info("uploadAjaxActionPOST..........");

	    /* �̹��� ���� üũ */
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

	    /* ��¥ ���� ��� */
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = new Date();
	    String str = sdf.format(date);
	    String datePath = str.replace("-", File.separator);

	    /* ���� ���� */
	    File uploadPath = new File(uploadFolder, datePath);
	    if (uploadPath.exists() == false) {
	        uploadPath.mkdirs();
	    }

	    /* �̹��� ���� ��� ��ü */
	    List<AttachImageVO> list = new ArrayList<>();

	    for (MultipartFile multipartFile : uploadFile) {
	        /* �̹��� ���� ��ü */
	        AttachImageVO vo = new AttachImageVO();
	        /* ���� �̸� */
	        String uploadFileName = multipartFile.getOriginalFilename();
	        vo.setFileName(uploadFileName);
	        vo.setUploadPath(datePath);

	        /* uuid ���� ���� �̸� */
	        String uuid = UUID.randomUUID().toString();
	        vo.setUuid(uuid);
	        uploadFileName = uuid + "_" + uploadFileName;

	        /* ���� ��ġ, ���� �̸��� ��ģ File ��ü */
	        File saveFile = new File(uploadPath, uploadFileName);

	        /* ���� ���� */
	        try {
	            multipartFile.transferTo(saveFile);

	            /* ����� ���� ��� 2 */
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

	/* �̹��� ���� ���� */
	@PostMapping("/deleteFile")
	public ResponseEntity<String> deleteFile(String fileName) {

		logger.info("deleteFile........" + fileName);

		File file = null;

		try {
			/* ����� ���� ���� */
			file = new File("c:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));

			file.delete();

			/* ���� ���� ���� */
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

	/* �ֹ� ��Ȳ ������ */
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
	
	//* �ֹ����� */
	@PostMapping("/orderCancle")
	public String orderCanclePOST(OrderCancelDTO dto) {
		
		orderService.orderCancle(dto);
		
		return "redirect:/admin/orderList?keyword=" + dto.getKeyword() + "&amount=" + dto.getAmount() + "&pageNum=" + dto.getPageNum();
	}

}
