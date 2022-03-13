package com.czf.computer.controller;

import com.czf.computer.domain.Order;
import com.czf.computer.service.OrderService;
import com.czf.computer.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/orders")
public class OrderController extends BaseController{

    @Resource
    private OrderService orderService;

    @RequestMapping("/create")
    public JsonResult<Order> create(int aid, int[] cids, HttpSession session) {
        // 从Session中取出uid和username
        int uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        Order data = orderService.create(aid, cids, uid, username);
        // 返回成功与数据
        return new JsonResult<>(OK, data);
    }
}
