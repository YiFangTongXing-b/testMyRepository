package com.czf.computer.service.impl;

import com.czf.computer.domain.Product;
import com.czf.computer.mapper.ProductMapper;
import com.czf.computer.service.ProductService;
import com.czf.computer.service.ex.ProductNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Override
    public List<Product> findHotList() {
        return productMapper.findHotList();
    }

    @Override
    public Product findById(int id) {
        Product product = productMapper.findById(id);
        if(product==null){
            throw new ProductNotFoundException("无此商品信息");
        }
        return productMapper.findById(id);
    }
}
