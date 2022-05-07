package com.lihan.scorelinequery.service;

import com.github.pagehelper.PageInfo;
import com.lihan.scorelinequery.entity.School;

public interface SchoolService {
    public PageInfo FindSchool(Integer pageNum,Integer pageSize,String school_name,String province_name);
    public void DeleteSchool(Integer school_id);
    public void InsertSchool(String school_name,Integer pid,String site);
    public void UpdateSchool(Integer school_id,String school_name,Integer pid,String site);
    public School SelectSchool(String school_name);
}
