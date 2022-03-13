package com.czf.computer.domain;

import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity {

    private String createdUser;     //创建人
    private Date createdTime;       //创建时间
    private String modifiedUser;    //最后修改的执行人
    private Date modifiedTime;      //最后修改的执行时间

}