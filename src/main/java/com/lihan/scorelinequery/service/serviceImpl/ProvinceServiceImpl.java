package com.lihan.scorelinequery.service.serviceImpl;

import com.lihan.scorelinequery.dao.ProvinceDao;
import com.lihan.scorelinequery.service.ProvinceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class ProvinceServiceImpl implements ProvinceService {
    @Resource
    ProvinceDao provinceDao;

    @Override
    public String getProvinceName(Integer pid) {
        return provinceDao.getProvinceName(pid);
    }

    @Override
    public Integer getProvincePid(String province_name) {
        return provinceDao.getProvincePid(province_name);
    }
}
