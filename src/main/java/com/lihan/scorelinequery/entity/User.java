package com.lihan.scorelinequery.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class User implements Serializable {
    private Integer uid;
    private String user_name;
    private String password;
}
