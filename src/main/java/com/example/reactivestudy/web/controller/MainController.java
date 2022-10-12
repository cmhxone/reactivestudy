package com.example.reactivestudy.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Controller
public class MainController {

    /**
     * 루트 요청 핸들러
     * 
     * @return
     */
    @RequestMapping(path = "/", method = { RequestMethod.GET })
    @ResponseBody
    public Flux getIndex() {

        Flux result = Flux.just(
                new String("Hello World..."),
                31,
                true).doOnNext(el -> {
                    log.info("getIndex(): {}", el);
                }).doOnError(err -> {
                    log.error("getIndex(): {}", err.getMessage());
                });

        return result;
    }

}
