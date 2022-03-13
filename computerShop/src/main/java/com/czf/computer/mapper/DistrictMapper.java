package com.czf.computer.mapper;

import com.czf.computer.domain.District;

import java.util.List;

public interface DistrictMapper {

    List<District> findByParent(String parent);     //根据父代号查询区

    String findNameByCode(String code);   //根据code查询名称
}
