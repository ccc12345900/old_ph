package com.zuo.controller;


import com.zuo.common.OldMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2022-12-05
 */
@RestController
@RequestMapping("/oldPh")
public class OldPhController {

    @PostMapping("/getMessage")
    public String getMessage(HttpSession session)
    {
        return new OldMessage().getOldMessage(session).toJSONString();
    }
}

