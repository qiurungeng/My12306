package com.example.neu.neuassigment.db;

import org.litepal.crud.DataSupport;

import lombok.Data;

@Data
public class Station extends DataSupport {
    private String name;
    private String code;
}
