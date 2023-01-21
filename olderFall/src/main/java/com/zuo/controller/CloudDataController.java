package com.zuo.controller;


import com.zuo.common.R;
import com.zuo.service.CloudDataService;
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
 * @since 2023-01-19
 */
@RestController
@RequestMapping("/cloudData")
public class CloudDataController {

    @Autowired
    private CloudDataService cloudDataService;

    @RequestMapping("/getcloudData")
    public R getcloudData(HttpSession session)
    {
        return cloudDataService.getCloudData(session);
    }
}

