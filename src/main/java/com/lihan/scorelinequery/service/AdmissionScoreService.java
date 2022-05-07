package com.lihan.scorelinequery.service;

import com.github.pagehelper.PageInfo;
import com.lihan.scorelinequery.entity.AdmissionScore;

public interface AdmissionScoreService {
    PageInfo<AdmissionScore> FindAdmissionScores(Integer pageNum, Integer pageSize,String school_name, String province_name,Integer year,
                                                 String major, String kind);

    void DeleteAdmissionScore(Integer admission_id);

    void InsertAdmissionScore(Integer pid, Integer school_id, String school_name, Integer year,
                              String major, String kind, Integer least_score, Integer high_score,
                              Integer avg_score, Integer least_rank, Integer num);

    AdmissionScore SelectAdmission(Integer pid, String school_name, Integer year,
                                   String major, String kind);

    void UpdateAdmissionScore(Integer admission_id,Integer pid, Integer school_id, String school_name, Integer year,
                              String major, String kind, Integer least_score, Integer high_score,
                              Integer avg_score, Integer least_rank, Integer num);
}
