package com.czf.computer.service;

import com.czf.computer.domain.Address;

import java.util.List;

//收货地址业务层接口
public interface AddressService {
    void addAddress(int uid, String username, Address address);

    List<Address> getAllAddressByUid(int uid);

    void setDefault(int aid,int uid,String username);        //修改某个地址为默认地址，前提是已经登陆了，所以有uid

    void delete(int aid,int uid,String username);    //删除用户选中的地址

    Address findByAid(int aid);          //根据aid查询出地址
}
