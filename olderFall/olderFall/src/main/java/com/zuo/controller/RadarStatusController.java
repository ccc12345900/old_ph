package com.zuo.controller;


import com.zuo.common.R;
import com.zuo.service.RadarStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2022-12-08
 */
@RestController
@RequestMapping("/radarStatus")
public class RadarStatusController {

    @Autowired
    private RadarStatusService radarStatusService;

    @PostMapping("/getradarStatus")
    public R getradarStatus(HttpSession session)
    {
        return radarStatusService.getMachineStatus(session);
    }
}

