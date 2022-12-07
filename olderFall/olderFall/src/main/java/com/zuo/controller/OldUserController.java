package com.zuo.controller;


import com.zuo.common.R;
import com.zuo.entity.request.OlderUserRequest;
import com.zuo.service.OldUserService;
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
@RequestMapping("/oldUser")
public class OldUserController {

    @Autowired
    private OldUserService oldUserService;

    @PostMapping("/login/status")
    public R loginStatus(@RequestBody OlderUserRequest olderUserRequest, HttpSession httpSession)
    {
        return oldUserService.verityPassword(olderUserRequest,httpSession);
    }
}

