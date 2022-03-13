package com.czf.computer.controller;

import com.czf.computer.domain.District;
import com.czf.computer.service.DistrictService;
import com.czf.computer.util.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/district")
public class DistrictController extends BaseController{

    @Resource
    private DistrictService districtService;

    //district开头的请求都拦截来这里
    @RequestMapping({"/",""})
    public JsonResult<List<District>> getByParent(String parent){
        List<District> data = districtService.getByParent(parent);
        return new JsonResult<>(OK,data);
    }
}
