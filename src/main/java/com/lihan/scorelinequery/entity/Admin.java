package com.lihan.scorelinequery.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Admin implements Serializable {
    private Integer admin_id;
    private String admin_name;
    private String admin_password;
}
