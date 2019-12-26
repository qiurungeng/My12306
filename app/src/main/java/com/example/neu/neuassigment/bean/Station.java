package com.example.neu.neuassigment.bean;

import org.litepal.crud.DataSupport;

import lombok.Data;

@Data
public class Station extends DataSupport {
    private String name;
    private String code;
}
