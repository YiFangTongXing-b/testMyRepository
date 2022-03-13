package com.czf.computer.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class District extends BaseEntity implements Serializable {
    private int id;
    private String parent;   //父区域的代号
    private String code;     //本身的代号1
    private String name;
}
