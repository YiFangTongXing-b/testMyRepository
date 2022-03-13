package com.czf.computer.service;

import com.czf.computer.vo.CartVO;

import java.util.List;

public interface CartService {
    /**
     * 将商品添加到购物车
     * @param uid 当前登录用户的id
     * @param pid 商品的id
     * @param amount 增加的数量
     * @param username 当前登录的用户名
     */
    void addToCart(Integer uid, Integer pid, Integer amount, String username);

    List<CartVO> getVOByUid(int uid);

    int addNum(int cid,int uid,String username);   //增加成功后，返回商品数量

    List<CartVO> getVOByCid(int[] cids);
}
