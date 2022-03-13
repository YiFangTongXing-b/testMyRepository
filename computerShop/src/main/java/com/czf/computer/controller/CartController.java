package com.czf.computer.controller;

import com.czf.computer.service.CartService;
import com.czf.computer.util.JsonResult;
import com.czf.computer.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController extends BaseController{
    @Autowired
    private CartService cartService;

    @RequestMapping("/add_to_cart")
    public JsonResult<Void> addToCart(int pid, int amount, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        cartService.addToCart(uid, pid, amount, username);
        return new JsonResult<>(OK);
    }

    @RequestMapping({"","/"})
    public JsonResult<List<CartVO>> getVOByUid(HttpSession session) {
        List<CartVO> list = cartService.getVOByUid(getUidFromSession(session));
        return new JsonResult<>(OK,list);
    }

    @RequestMapping("/{cid}/num/add")
    public JsonResult<Integer> addNum(@PathVariable("cid") int cid, HttpSession session) {
        int uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        int num = cartService.addNum(cid,uid,username);
        return new JsonResult<>(OK,num);
    }

    @RequestMapping("/list")
    public JsonResult<List<CartVO>> getVOByCid(int[] cids) {
        List<CartVO> list = cartService.getVOByCid(cids);
        return new JsonResult<>(OK,list);
    }
}
