package com.example.neu.neuassigment.db;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

import lombok.Data;

@Data
public class User extends DataSupport implements Serializable {
    String username;
    String password;
    String name;
    String userType;
    String idCardType;
    String idCardNumber;
    String phoneNumber;
}
