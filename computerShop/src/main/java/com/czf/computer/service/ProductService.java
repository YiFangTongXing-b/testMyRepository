package com.czf.computer.service;

import com.czf.computer.domain.Product;


import java.util.List;


public interface ProductService {
    List<Product> findHotList();    //查找出热销商品的前4个

    Product findById(int id);
}
