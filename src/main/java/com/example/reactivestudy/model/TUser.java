package com.example.reactivestudy.model;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class TUser {

    @Id
    @NonNull
    private UUID uuid;

    @NonNull
    private String userID;

    @NonNull
    private String userPassword;

    @NonNull
    private String userName;

    @NonNull
    private Timestamp createDatetime;

    private Timestamp lastLoginDatetime;

    @NonNull
    private Boolean loginEnabled;

    private InetAddress lastLoginAddress;

    @NonNull
    private Integer userPoint;
}
