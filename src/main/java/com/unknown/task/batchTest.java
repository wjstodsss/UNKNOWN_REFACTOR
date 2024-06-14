package com.unknown.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class batchTest {

    @Scheduled
    public void testMeThod() throws Exception{
        
        log.warn("배치 실행 테스트입니다.");
        log.warn("===============");
    }
}
