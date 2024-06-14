package com.unknown.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unknown.model.BenefitsReplyVO;
import com.unknown.model.Criteria;
import com.unknown.service.BenefitsReplyService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/replies1/*")
@AllArgsConstructor
@NoArgsConstructor
@Log4j
public class BenefitsReplyController {
	@Autowired
	private BenefitsReplyService service;
	
	@GetMapping("/test")	
	public String testReply() {
		return "test";   // @RestController 문자열만 전송
	}

	@PostMapping(value = "/new", consumes = "application/json", produces = { MediaType.TEXT_HTML_VALUE })
	public ResponseEntity<String> create(@RequestBody BenefitsReplyVO vo) {
		log.info("BenefitsReplyVO" + vo);
		int insertCount = service.register(vo);
		return insertCount == 1 ? new ResponseEntity<String>("success", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping(value = "/pages/{benefitsId}/{page}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<BenefitsReplyVO>> getList(@PathVariable("benefitsId") Long benefitsId, @PathVariable("page") int page) {
		
		log.info("getList..........");
		Criteria cri = new Criteria(page, 10);

		return new ResponseEntity<List<BenefitsReplyVO>>(service.getList(), HttpStatus.OK);

	}

	//  반환타입 json으로 가져오기
	@GetMapping(value = "/{benefitsReplyId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<BenefitsReplyVO> get(@PathVariable("benefitsReplyId") Long benefitsReplyId) {
		log.info("12312312321312ffffffff213213213");
		log.info("get: " + benefitsReplyId);
		return new ResponseEntity<>(service.get(benefitsReplyId), HttpStatus.OK);
	}

	// 반환타입 문자
	@DeleteMapping(value = "/{benefitsReplyId}", produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> remove(@PathVariable("benefitsReplyId") Long benefitsReplyId) {
		log.info("remove: " + benefitsReplyId);
		return service.remove(benefitsReplyId) == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
//	 @RequestMapping(method = RequestMethod.PUT, value = "/{benefitsReplyId}", consumes =
//	 "application/json", produces = { MediaType.TEXT_PLAIN_VALUE }) public
//	 ResponseEntity<String> modify(@RequestBody BenefitsReplyVO
//	 vo, @PathVariable("benefitsReplyId") Long benefitsReplyId) {
//	 
//	 vo.setBenefitsReplyId(benefitsReplyId);
//	 
//	 log.info(vo);
//	 
//	 log.info("benefitsReplyId: " + benefitsReplyId); log.info("modify: " + vo);
//	 
//	 return service.modify(vo) == 1 ? new ResponseEntity<>("success",
//	 HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//	 
//	 }
	
}
