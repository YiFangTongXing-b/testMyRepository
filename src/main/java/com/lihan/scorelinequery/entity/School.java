package com.lihan.scorelinequery.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class School implements Serializable {
    private Integer school_id;
    private Integer pid;
    private String school_name;
    private String province_name;
    private String site;
    private String score_site;
}
