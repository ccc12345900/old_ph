package com.zuo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author 橙宝cc
 * @date 2022/12/5 - 22:49
 */
@Controller
public class IndexController {

    @RequestMapping("/management")
    public String management(HttpSession session)
    {
        if(session.getAttribute("name")!=null)
            return "management";
        else
            return "index";
    }
}
