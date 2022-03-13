package com.czf.computer.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Cart extends BaseEntity implements Serializable {
    private int cid;
    private int uid;
    private int pid;
    private Long price;
    private int num;
}
