package com.czf.computer.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Order extends BaseEntity implements Serializable {
    private int oid;
    private int uid;
    private String recvName;
    private String recvPhone;
    private String recvProvince;
    private String recvCity;
    private String recvArea;
    private String recvAddress;
    private Long totalPrice;
    private int status;
    private Date orderTime;
    private Date payTime;
}
