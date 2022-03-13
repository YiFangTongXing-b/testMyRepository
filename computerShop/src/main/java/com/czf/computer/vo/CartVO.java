package com.czf.computer.vo;

import lombok.Data;

import java.io.Serializable;

/** 购物车数据的Value Object类 */
@Data
public class CartVO implements Serializable {
    private int cid;
    private int uid;
    private int pid;
    private Long price;
    private int num;
    private String title;
    private Long realPrice;
    private String image;


}
