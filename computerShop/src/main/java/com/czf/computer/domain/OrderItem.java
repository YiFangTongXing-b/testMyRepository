package com.czf.computer.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderItem extends BaseEntity implements Serializable {
    private int id;
    private int oid;
    private int pid;
    private String title;
    private String image;
    private Long price;
    private int num;

}
