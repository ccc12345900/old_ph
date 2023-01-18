package com.zuo.controller;


import com.zuo.common.R;
import com.zuo.service.DoorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2023-01-18
 */
@RestController
@RequestMapping("/doorData")
public class DoorDataController {

    @Autowired
    private DoorDataService doorDataService;

    @RequestMapping("/getdoor")
    public R getDoor(HttpSession session)
    {
        return doorDataService.getDoor(session);
    }

    @RequestMapping("/createdoor")
    public R createDoor(HttpSession session)
    {
        return doorDataService.createDoor(session);
    }
}

