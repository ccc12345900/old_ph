package com.zuo.controller;


import com.zuo.common.R;
import com.zuo.entity.request.OlderUserRequest;
import com.zuo.service.FamilyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2023-01-16
 */
@RestController
@RequestMapping("/familyData")
public class FamilyDataController {

    @Autowired
    private FamilyDataService familyDataService;

    @GetMapping("/getFamily")
    public R getFamily(HttpSession session){
        return familyDataService.getFamilyData(session);
    }

    @PostMapping("/oldGetFamilyData")
    public R oldGetFamilyData(@RequestBody OlderUserRequest olderUserRequest){
        return familyDataService.getFamilyDataByOid(olderUserRequest.getOid());
    }
}

/**
 * docker run -di --name rabbitmq -p 5673:5672 -p 15672:15672 -v `pwd`/rabbitmq:/var/lib/rabbitmq --hostname myRabbit -e RABBITMQ_DEFAULT_VHOST=myvhost -e RABBITMQ_DEFAULT_USER=root -e RABBITMQ_DEFAULT_PASS=123456 rabbitmq
 */

