package com.czf.computer.mapper;

import com.czf.computer.domain.Address;
import com.czf.computer.domain.District;

import java.util.Date;
import java.util.List;

//收获地址
public interface AddressMapper {

    int insert(Address address);     //插入收货地址

    int countByUid(int uid);         //根据用户数量查询该用户有多少个地址

    List<Address> findAllAddressByUid(int uid);    //根据用户id查询所有收货地址

    Address findAddressByAid(int aid);    //点击设置默认，需要先判断地址是否还存在

    int updateNonDefault(int uid);        //根据用户uid，先将所有地址设为非默认

    int updateDefaultByAid(int aid, String modifiedUser, Date modifiedTime);    //最终根据aid将点击的地址设为默认地址

    int deleteAddressByAid(int aid);                 //根据aid删除地址

    Address findLastModified(int uid);               //查询出用户最近修改的地址


}
