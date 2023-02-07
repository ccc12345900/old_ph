package com.zuo.controller;


import com.zuo.common.OldMessage;
import com.zuo.common.R;
import com.zuo.entity.request.OlderUserRequest;
import com.zuo.mapper.OldPhMapper;
import com.zuo.service.OldPhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Autowired
    private OldPhService oldPhService;
    @PostMapping("/getMessage")
    public String getMessage(HttpSession session)
    {
        return new OldMessage().getOldMessage(session).toJSONString();
    }

    @PostMapping("/getOldData")
    public R getOldData(@RequestBody OlderUserRequest olderUserRequest){
        return oldPhService.getOldPhData(olderUserRequest.getOid());
    }
}

