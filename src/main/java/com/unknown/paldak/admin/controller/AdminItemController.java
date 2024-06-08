package com.unknown.paldak.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.common.domain.PageDTO;
import com.unknown.paldak.admin.domain.AttachImageVO;
import com.unknown.paldak.admin.domain.ItemCateVO;
import com.unknown.paldak.admin.domain.ItemVO;
import com.unknown.paldak.admin.domain.ReviewReplyVO;
import com.unknown.paldak.admin.service.AdminAttachServiceImpl;
import com.unknown.paldak.admin.service.BaseService;
import com.unknown.paldak.admin.service.AdminItemCateServiceImpl;
import com.unknown.paldak.admin.service.AdminItemServiceImpl;
import com.unknown.paldak.admin.util.AdminFileUploadManager;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin/item/*")
@RequiredArgsConstructor
public class AdminItemController {

	private final BaseService<ItemVO> itemService;
    private final AdminFileUploadManager fileUploadManager;
    private final AdminAttachServiceImpl attachService;
    private final AdminItemCateServiceImpl itemCateService;
    private final AdminItemServiceImpl itemServiceUtil;


	@GetMapping("/list")
	public String list(Criteria cri, Model model) {
		System.out.println(cri);
		List<ItemCateVO> cateList = itemCateService.getList();
		cateList.forEach(itemVO -> System.out.println(itemVO + "kkkkkkkkkkkkkkkk"));
		List<ItemVO> list = itemService.getList(cri);
		list.forEach(itemVO -> System.out.println(itemVO + "kkkkkkkkkkkkkkkk"));
		model.addAttribute("items", list);
		model.addAttribute("categorys", cateList);
		
	
        int total = itemService.getTotal(cri);
        
        model.addAttribute("pageMaker", new PageDTO(cri, total));     
        return "admin/item";
	}

	
	@GetMapping("/descList")
	public String descList(Criteria cri, Model model) {
		List<ItemCateVO> cateList = itemCateService.getList();
		List<ItemVO> list = itemService.getDescList(cri);
		int total = itemService.getTotal(cri);
		model.addAttribute("items", list);
        model.addAttribute("categorys", cateList);
        model.addAttribute("pageMaker", new PageDTO(cri, total));
        return "admin/item";
	}
	
	

	@PostMapping("/register")
	public String register(@RequestParam("uploadFile") MultipartFile[] uploadFile, Model model, AttachImageVO attachItemVO, ItemVO itemVO, RedirectAttributes rttr) {
        System.out.println("kkkk");
       
        
        
       
        itemService.register(itemVO);
        long newId = itemVO.getItemId();
        if (!uploadFile[0].isEmpty()) { 		
			Map<String, String> imageInfo = fileUploadManager.uploadFiles(uploadFile);
			String uuid = imageInfo.get("uuid");
			String fileName = imageInfo.get("originalFilename");
			String uploadPath = imageInfo.get("datePath");
			attachItemVO.setUuid(uuid);
			attachItemVO.setFileName(fileName);
			attachItemVO.setUploadPath(uploadPath);
			attachItemVO.setItemId(newId);
	        System.out.println(attachItemVO + "sdkhfklsahflhslfkd");
	        int result = attachService.register(attachItemVO);

	        
		    if(result<1) {
		    	System.out.println("?´ë?¸ì???ë³? ???? ?¤í??");
		    	return "error";
		    }
		}
        
        boolean itemStateResult = itemServiceUtil.registerItemState(itemVO);
        if(!itemStateResult) {
        	return "error";
        }
	    rttr.addFlashAttribute("result", newId);
	    return "redirect:descList";
	}

	@GetMapping(value = "/get/{itemId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String, Object>> get(@PathVariable("itemId") long itemId) {
	    ItemVO item = itemService.get(itemId);
	    System.out.println(item);
	    Map<String, Object> responseData = new HashMap<>();
	    responseData.put("item", item);

	    // ResponseEntity?? JSON ?°ì?´í?? ë°???
	    return new ResponseEntity<>(responseData, HttpStatus.OK);
	}
	
	@GetMapping(value = "/itemState/{itemId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ItemVO> modifyItemState(@PathVariable("itemId") long itemId) {
		System.out.println("900999999999999999999999999999999999999");
		ItemVO itemVO = new ItemVO();
		itemVO.setItemId(itemId);
		itemVO.setItemState("????");
	    
	    boolean result = itemServiceUtil.modifyItemState(itemVO);
	    
	    System.out.println(result);
	    if (result) {
	        return new ResponseEntity<>(itemVO, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@GetMapping(value = "/checkItem/{itemId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Map<String, Boolean>> checkDuplicateId(@PathVariable("itemId") long itemId) {
		
		boolean result = false;
		ItemVO itemVO = itemService.get(itemId);
		System.out.println(itemVO);
        if(itemVO != null) {
        	result = true;
        }
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("result", result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@PostMapping("/modify")
	public String modify(MultipartFile[] uploadFile, ItemVO itemVO, @ModelAttribute("cri") Criteria cri, AttachImageVO attachItemVO, ReviewReplyVO replyVO, @RequestParam("currentPath") String currentPath, RedirectAttributes rttr) {
		
	        long currentItemId = itemVO.getItemId();
	        if (!uploadFile[0].isEmpty()) { 		
				Map<String, String> imageInfo = fileUploadManager.uploadFiles(uploadFile);
				String uuid = imageInfo.get("uuid");
				String fileName = imageInfo.get("originalFilename");
				String uploadPath = imageInfo.get("datePath");
				attachItemVO.setUuid(uuid);
				attachItemVO.setFileName(fileName);
				attachItemVO.setUploadPath(uploadPath);
				attachItemVO.setItemId(currentItemId);
		        System.out.println(attachItemVO + "sdkhfklsahflhslfkd");
		        attachService.modify(attachItemVO);		     
			}
		
		
		
		System.out.println("jh-----------------///////////kjhjk");
        if (!uploadFile[0].isEmpty()) { 
			String imageURL = fileUploadManager.uploadFiles(uploadFile).get("imageURLs");
			itemVO.setItemImageURL(imageURL);
		}

	    if (itemService.modify(itemVO)) {
	        rttr.addFlashAttribute("result", "success");
	    }
	    
	    rttr.addAttribute("pageNum", cri.getPageNum());
	    rttr.addAttribute("amount", cri.getAmount());
	    return "redirect:" + currentPath;
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("itemId") Long itemId, @ModelAttribute("cri") Criteria cri, @RequestParam("currentPath") String currentPath, RedirectAttributes rttr) {
		
		String itemImageURL = itemService.get(itemId).getItemImageURL();
		
		System.out.println("remove..." + itemId);
		if (itemService.remove(itemId)) {
			rttr.addFlashAttribute("result", "success");
		}
		
		fileUploadManager.deleteFile(itemImageURL);
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		System.out.println("remove..." + itemId);
		return "redirect:" + currentPath;
	}
	

}