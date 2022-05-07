package com.lihan.scorelinequery.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lihan.scorelinequery.dao.SchoolDao;
import com.lihan.scorelinequery.entity.School;
import com.lihan.scorelinequery.service.SchoolService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Resource
    SchoolDao schoolDao;


    @Override
    public PageInfo FindSchool(Integer pageNum, Integer pageSize, String school_name, String province_name) {
        PageHelper.startPage(pageNum,pageSize);
        List<School> schoolList=schoolDao.SelectSchools(school_name,province_name);
        System.out.println("============="+schoolList);
        return new PageInfo<School>(schoolList);
    }

    @Override
    public void DeleteSchool(Integer school_id) {
        schoolDao.DeleteSchool(school_id);
    }

    @Override
    public void InsertSchool(String school_name, Integer pid, String site) {
        schoolDao.InsertSchool(school_name,pid,site);
    }

    @Override
    public void UpdateSchool(Integer school_id,String school_name, Integer pid, String site) {
        schoolDao.UpdateSchool(school_id,school_name,pid,site);
    }

    @Override
    public School SelectSchool(String school_name) {
        return schoolDao.SelectSchool(school_name);
    }

}
