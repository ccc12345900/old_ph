package com.zuo.controller;


import com.zuo.common.R;
import com.zuo.entity.ControllUser;
import com.zuo.entity.request.ControllUserRequest;
import com.zuo.entity.request.FmUserRequest;
import com.zuo.service.ControllUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * @since 2023-02-07
 */
@RestController
@RequestMapping("/controllUser")
public class ControllUserController {

    @Autowired
    private ControllUserService controllUserService;

    @PostMapping("/login")
    public R login(@RequestBody ControllUserRequest controllUser, HttpSession session){
        return controllUserService.verifyControlUser(controllUser,session);
    }
}

