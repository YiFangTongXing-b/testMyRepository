package com.czf.computer.mapper;

import com.czf.computer.domain.Product;

import java.util.List;

public interface ProductMapper {

    List<Product> findHotList();   //找出热销商品

    Product findById(int id);       //根据id查找出商品信息
}
