package com.lihan.scorelinequery.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lihan.scorelinequery.dao.AdmissionScoreDao;
import com.lihan.scorelinequery.entity.AdmissionScore;
import com.lihan.scorelinequery.service.AdmissionScoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdmissionScoreServiceImpl implements AdmissionScoreService {
    @Resource
    AdmissionScoreDao admissionScoreDao;

    @Override
    public PageInfo<AdmissionScore> FindAdmissionScores(Integer pageNum, Integer pageSize, String school_name,
                                                        String province_name, Integer year, String major, String kind) {
        PageHelper.startPage(pageNum, pageSize);
        List<AdmissionScore> admissionScoreList = admissionScoreDao.SelectAdmissionScores(school_name, province_name, year, major, kind);
        return new PageInfo<AdmissionScore>(admissionScoreList);
    }

    @Override
    public void DeleteAdmissionScore(Integer admission_id) {
        admissionScoreDao.DeleteAdmissionScore(admission_id);
    }

    @Override
    public void InsertAdmissionScore(Integer pid, Integer school_id, String school_name,
                                     Integer year, String major, String kind, Integer least_score,
                                     Integer high_score, Integer avg_score, Integer least_rank, Integer num) {
        admissionScoreDao.InsertAdmissionScore(pid, school_id, school_name, year, major, kind, least_score, high_score, avg_score, least_rank, num);
    }

    @Override
    public AdmissionScore SelectAdmission(Integer pid, String school_name, Integer year, String major, String kind) {
        return admissionScoreDao.SelectAdmissionScore(pid, school_name, year, major, kind);
    }

    @Override
    public void UpdateAdmissionScore(Integer admission_id, Integer pid, Integer school_id, String school_name, Integer year, String major, String kind, Integer least_score, Integer high_score, Integer avg_score, Integer least_rank, Integer num) {
        admissionScoreDao.UpdateAdmissionScore(admission_id, pid, school_id, school_name, year, major, kind, least_score, high_score, avg_score, least_rank, num);
    }
}
