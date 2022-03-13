package com.czf.computer.service.impl;

import com.czf.computer.domain.Cart;
import com.czf.computer.domain.Product;
import com.czf.computer.mapper.CartMapper;
import com.czf.computer.mapper.ProductMapper;
import com.czf.computer.service.CartService;
import com.czf.computer.service.ex.CartNotFoundException;
import com.czf.computer.service.ex.InsertException;
import com.czf.computer.vo.CartVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Resource
    private CartMapper cartMapper;
    @Resource
    private ProductMapper productMapper;

    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {
        // 根据参数pid和uid查询购物车中的数据
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        if (result == null) {
            Cart cart = new Cart();
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
            //查询商品数据，得到商品信息，再得到商品的价格
            Product product = productMapper.findById(pid);
            cart.setPrice(product.getPrice());
            // 封装数据：4个日志
            cart.setCreatedUser(username);
            cart.setCreatedTime(new Date());
            cart.setModifiedUser(username);
            cart.setModifiedTime(new Date());
            // 调用insert(cart)执行将数据插入到数据表中
            int rows = cartMapper.insert(cart);
            if (rows != 1) {
                throw new InsertException("插入商品数据时出现未知错误，请联系系统管理员");
            }
        } else {
            //表示该用户的购物车中已有该商品,从查询结果中获取购物车数据的id
            int cid = result.getCid();
            // 从查询结果中取出原数量，与参数amount相加，得到新的数量
            int num = result.getNum() + amount;
            // 执行更新数量
            int rows = cartMapper.updateNumByCid(cid, num, username, new Date());
            if (rows != 1) {
                throw new InsertException("修改商品数量时出现未知错误，请联系系统管理员");
            }
        }
    }

    @Override
    public List<CartVO> getVOByUid(int uid) {
        return cartMapper.findVOByUid(uid);
    }

    @Override
    public int addNum(int cid, int uid, String username) {
        Cart result = cartMapper.findByCid(cid);
        if(result==null){
            throw new CartNotFoundException("购物车不存在");
        }
        int num = result.getNum()+1;
        cartMapper.updateNumByCid(cid,num,username,new Date());
        return num;
    }

    @Override
    public List<CartVO> getVOByCid(int[] cids) {
        return cartMapper.findVOByCids(cids);
    }
}
