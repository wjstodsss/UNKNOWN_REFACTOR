package com.unknown.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.unknown.model.Criteria;
import com.unknown.model.QNAReplyVO;
import com.unknown.service.QNAReplyService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/replies2/*")
@AllArgsConstructor
@NoArgsConstructor
@Log4j
public class QNAReplyController {
    @Autowired
    private QNAReplyService service;

    @GetMapping("/test")    
    public String testReply() {
        return "test";   // @RestController 문자열만 전송
    }

    @PostMapping(value = "/new", consumes = "application/json", produces = { MediaType.TEXT_HTML_VALUE })
    public ResponseEntity<String> create(@RequestBody QNAReplyVO vo) {
        log.info("QNAReplyVO" + vo);
        int insertCount = service.register(vo);
        return insertCount == 1 ? new ResponseEntity<String>("success", HttpStatus.OK)
                : new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/pages/{qnaId}/{page}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<QNAReplyVO>> getList(@PathVariable("qnaId") Long qnaId, @PathVariable("page") int page) {
        log.info("getList..........");
        Criteria cri = new Criteria(page, 10);
        List<QNAReplyVO> replies = service.getList(cri, qnaId);
        log.info("Response List: " + replies);
        return new ResponseEntity<>(replies, HttpStatus.OK);
    }

    @GetMapping(value = "/{replyId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<QNAReplyVO> get(@PathVariable("replyId") Long replyId) {
        log.info("get: " + replyId);
        return new ResponseEntity<>(service.get(replyId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{replyId}", produces = { MediaType.TEXT_PLAIN_VALUE })
    public ResponseEntity<String> remove(@PathVariable("replyId") Long replyId) {
        log.info("remove: " + replyId);
        return service.remove(replyId) == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{replyId}", consumes = "application/json", produces = {
            MediaType.TEXT_PLAIN_VALUE })
    public ResponseEntity<String> modify(@RequestBody QNAReplyVO vo, @PathVariable("replyId") Long replyId) {
        vo.setQnaId(replyId);
        log.info("modify: " + vo);
        return service.modify(vo) == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
