package com.zuo.controller;


import com.zuo.entity.request.FmUserRequest;
import com.zuo.common.R;
import com.zuo.entity.request.OlderUserRequest;
import com.zuo.service.FmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2022-12-03
 */
@RestController
@RequestMapping("/fmUser")
public class FmUserController {

    @Autowired
    private FmUserService fmUserService;

    @PostMapping("/login/status")
    public R loginStatus(@RequestBody FmUserRequest fmUserRequest, HttpSession httpSession){
        return fmUserService.verfyFmUser(fmUserRequest,httpSession);
    }

    @PostMapping("/familyList")
    public R familyList(HttpSession session){
        return fmUserService.familyPeopleNum(session);
    }

    @PostMapping("/deleteFmUser")
    public R deleteFmUser(@RequestBody FmUserRequest fmUserRequest,HttpSession session){
        return fmUserService.deleteFmUser(fmUserRequest,session);
    }

    @PostMapping("/getOldList")
    public R getOldList(@RequestBody FmUserRequest fmUserRequest){
        return fmUserService.getOldList(fmUserRequest.getFid());
    }
}

