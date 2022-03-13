package com.czf.computer.controller;

import com.czf.computer.controller.BaseController;
import com.czf.computer.domain.Address;
import com.czf.computer.service.AddressService;
import com.czf.computer.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/address")
@RestController
public class AddressController extends BaseController {

    @Autowired
    private AddressService addressService;

    @RequestMapping("/add_address")
    public JsonResult<Void> addAddress(Address address, HttpSession session){
        int uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.addAddress(uid,username,address);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/allAddress")
    public JsonResult<List<Address>> getAllAddressByUid(HttpSession session){
        int uid = getUidFromSession(session);
        List<Address> list = addressService.getAllAddressByUid(uid);
        return new JsonResult<>(OK,list);
    }

    @RequestMapping("/set_default/{aid}")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid, HttpSession session) {
        int uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.setDefault(aid, uid, username);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/delete/{aid}")
    public JsonResult<Void> delete(@PathVariable("aid")int aid, HttpSession session){
        addressService.delete(aid,getUidFromSession(session),getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }

}
