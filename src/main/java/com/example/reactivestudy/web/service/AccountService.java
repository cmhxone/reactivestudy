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

    public Mono<TUser> getUser(final String userID) {
        return accountRepository.findByUserID(userID);
    }

    @Transactional
    public Mono<TUser> putUser(final TUser user) {

        return accountRepository.findByUserID(user.getUserID())
                .switchIfEmpty(accountRepository.save(user));
    }
}
