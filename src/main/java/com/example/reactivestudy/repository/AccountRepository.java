package com.example.reactivestudy.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.reactivestudy.model.TUser;

import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<TUser, UUID> {

    Mono<TUser> findByUserID(String userID);
    Mono<TUser> save(TUser user);
}
