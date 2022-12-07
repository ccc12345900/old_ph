package com.zuo.controller;


import com.zuo.entity.request.FmUserRequest;
import com.zuo.service.OldFamilyService;
import com.zuo.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/oldFamily")
public class OldFamilyController {

    @Autowired
    private OldFamilyService oldFamilyService;

    @GetMapping("/getFamilyMessage")
    public  R getFamilyMessage(HttpSession httpSession)
    {
        return oldFamilyService.getFamilyMessage(httpSession);
    }

    @PostMapping("/bindFamily")
    public R bindFamily(@RequestBody FmUserRequest fmUserRequest, HttpSession session)
    {
        return oldFamilyService.bindFamily(fmUserRequest,session);
    }

    @PostMapping("/untieFamily")
    public R UntieFamily(@RequestBody FmUserRequest fmUserRequest,HttpSession session)
    {
        return oldFamilyService.untieFamily(fmUserRequest,session);
    }
}

