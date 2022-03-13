package com.czf.computer.service;

import com.czf.computer.domain.District;

import java.util.List;


public interface DistrictService {
    List<District> getByParent(String parent);    //根据父查询子。 例如省->市    市->区

    String getNameByCode(String code);


}
