package com.example.reactivestudy.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reactivestudy.model.TUser;
import com.example.reactivestudy.repository.AccountRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * 사용자 ID로 사용자 정보 검색
     * 
     * @param userID
     * @return
     */
    public Mono<TUser> getUser(final String userID) {
        return accountRepository.findByUserID(userID);
    }

    /**
     * 사용자 등록
     * 
     * @param user
     * @return
     */
    @Transactional
    public Mono<TUser> putUser(final TUser user) {

        /* Postgresql R2DBC Repository에서 save 처리 체크로직에, upsert 로직으로 구현되어 있지 않다. (무조건 key값을 가지고 insert만 수행)
         * upsert 처리를 위해서는, 동일 key의 데이터가 DB에 존재하는지 체크 후 dto의 클론을 생성해서 update 하도록 구현하도록 변경하자.
        */
        return accountRepository.findByUserID(user.getUserID())
                .switchIfEmpty(accountRepository.save(user));
    }
}
