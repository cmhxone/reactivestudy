package com.example.reactivestudy.web.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.reactivestudy.model.TUser;
import com.example.reactivestudy.util.EncryptionUtil;
import com.example.reactivestudy.web.service.AccountService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
@RequestMapping("/user")
public class AccountController {
    
    @Autowired
    private AccountService accountService;

    /**
     * 웹 사용자 계정 요청 핸들러
     * 
     * @param userID
     * @return
     */
    @RequestMapping(path = "/get", method = { RequestMethod.GET })
    @ResponseBody
    public Mono getUser(@RequestParam(name = "userid") String userID) {

        return accountService.getUser(userID).doOnNext(el -> {
            log.info("getUser(): {}", el.toString());
        }).doOnError(e -> {
            log.error("getUser(): {}", e.getMessage());
        });
    }

    /**
     * 웹 사용자 등록 핸들러
     * 
     * @param userlist
     * @return
     */
    @RequestMapping(path = "/put", method = { RequestMethod.POST })
    @ResponseBody
    public Mono putUser(@RequestBody() TUser pUser) {

        log.info("pUser.id={}, pUser.pw={}, pUser.name={}", pUser.getUserID(), pUser.getUserPassword(), pUser.getUserName());

        if ( Objects.isNull(pUser.getUserID()) || Objects.isNull(pUser.getUserPassword()) || Objects.isNull(pUser.getUserName())) {
            return Mono.error(new Throwable("Empty args..."));
        }

        TUser insertData = new TUser();
        insertData.setUserID(pUser.getUserID());
        insertData.setUserPassword(EncryptionUtil.hashing(pUser.getUserPassword(), "SHA-256").get());
        insertData.setUserName(pUser.getUserName());
        insertData.setCreateDatetime(Timestamp.valueOf(LocalDateTime.now()));
        insertData.setLoginEnabled(true);
        insertData.setUserPoint(0);
        insertData.setLastLoginDatetime(null);
        insertData.setLastLoginAddress(null);

        return accountService.putUser(insertData);
    }
}
