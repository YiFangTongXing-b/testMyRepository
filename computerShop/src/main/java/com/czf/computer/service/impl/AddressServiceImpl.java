package com.czf.computer.service.impl;

import com.czf.computer.controller.ex.AddressNotFoundException;
import com.czf.computer.controller.ex.DeleteException;
import com.czf.computer.domain.Address;
import com.czf.computer.mapper.AddressMapper;
import com.czf.computer.mapper.DistrictMapper;
import com.czf.computer.service.AddressService;
import com.czf.computer.service.DistrictService;
import com.czf.computer.service.ex.AddressCountLimitException;
import com.czf.computer.service.ex.InsertException;
import com.czf.computer.service.ex.UpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Resource
    private AddressMapper addressMapper;

    //添加收货地址的业务层 依赖于 省市区的业务层
    @Resource
    private DistrictService districtService;

    @Value("${address.max-count}")
    private int maxCount;

    @Override
    public void addAddress(int uid, String username, Address address) {
        int count = addressMapper.countByUid(uid);
        if(count>=maxCount){
            throw new AddressCountLimitException("收获地址超出20个");
        }

        // 补全数据：省、市、区的名称
        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);


        address.setUid(uid);
        int isDefault = count == 0? 1:0 ;   //1表示插入的地址为默认地址
        address.setIsDefault(isDefault);
        //补全4项
        address.setCreatedUser(username);
        address.setModifiedUser(username);
        address.setModifiedTime(new Date());
        address.setCreatedTime(new Date());
        //插入地址
        int result = addressMapper.insert(address);
        if(result != 1 ){
            throw new InsertException("地址添加异常");
        }
    }

    //根据id查询出该用户所有地址
    @Override
    public List<Address> getAllAddressByUid(int uid) {
        return addressMapper.findAllAddressByUid(uid);
    }


    @Override
    public void setDefault(int aid, int uid, String username) {
        Address address = addressMapper.findAddressByAid(aid);
        //省略对address为空的判断和抛异常
        int non = addressMapper.updateNonDefault(uid);
        int row = addressMapper.updateDefaultByAid(aid,username,new Date());
        //省略对row为空的判断和抛异常
    }


    @Override
    public void delete(int aid, int uid, String username) {
        // 根据参数aid，findAddressByAid()查询收货地址数据
        Address result = addressMapper.findAddressByAid(aid);
        if (result == null) {
            throw new AddressNotFoundException("收货地址数据不存在");
        }

        // 根据参数aid，调用deleteAddressByAid()执行删除
        int rows1 = addressMapper.deleteAddressByAid(aid);
        if(rows1 !=1 ){
            throw new DeleteException("删除失败");
        }

        // 判断查询结果中的isDefault是否为0
        if (result.getIsDefault() == 0) {
            return;
        }

        // 调用持久层的countByUid()统计目前还有多少收货地址
        int count = addressMapper.countByUid(uid);
        // 判断目前的收货地址的数量是否为0
        if (count == 0) {
            return;
        }

        // 调用findLastModified()找出用户最近修改的收货地址
        Address lastModified = addressMapper.findLastModified(uid);
        // 从以上查询结果中找出aid属性值
        int lastModifiedAid = lastModified.getAid();
        // 调用持久层的updateDefaultByAid()方法执行设置默认收货地址，并获取返回的受影响的行数
        int rows2 = addressMapper.updateDefaultByAid(lastModifiedAid, username, new Date());
        // 判断受影响的行数是否不为1
        if (rows2 != 1) {
            throw new UpdateException("更新收货地址数据时出现未知错误");
        }

        
    }

    @Override
    public Address findByAid(int aid) {
        return addressMapper.findAddressByAid(aid);
    }
}
