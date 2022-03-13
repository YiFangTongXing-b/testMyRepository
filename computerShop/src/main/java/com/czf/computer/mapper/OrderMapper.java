package com.czf.computer.mapper;

import com.czf.computer.domain.Order;
import com.czf.computer.domain.OrderItem;

public interface OrderMapper {
    int insertOrder(Order order);                   //插入订单
    int insertOrderItem(OrderItem orderItem);       //插入订单项
}
