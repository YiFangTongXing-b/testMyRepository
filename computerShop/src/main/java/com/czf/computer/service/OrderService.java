package com.czf.computer.service;


import com.czf.computer.domain.Order;


public interface OrderService {
    Order create(int aid, int[] cids, int uid, String username);
}
