package com.lihan.scorelinequery.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class AdmissionScore implements Serializable {
    private Integer admission_id;
    private Integer pid;
    private String province_name;
    private Integer school_id;
    private String school_name;
    private Integer year;
    private String major;
    private String kind;
    private Integer least_score;
    private Integer high_score;
    private Integer avg_score;
    private Integer least_rank;
    private Integer num;
}
