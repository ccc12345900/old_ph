package com.zuo.controller;

import com.zuo.common.R;
import com.zuo.entity.FmUser;
import com.zuo.entity.OldUser;
import com.zuo.entity.request.RegisterRequest;
import com.zuo.service.FmUserService;
import com.zuo.service.OldUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 橙宝cc
 * @date 2022/12/7 - 18:07
 */
@RestController
public class RegisterController {

    @Autowired
    private FmUserService fmUserService;

    @Autowired
    private OldUserService oldUserService;

    @PostMapping("/register/status")
    public R registerStatus(@RequestBody RegisterRequest registerRequest){
        if(registerRequest.getType()==1)
        {
            OldUser oldUser = new OldUser();
            oldUser.setName(registerRequest.getName());
            oldUser.setPassword(registerRequest.getPassword());
            oldUser.setPhone(registerRequest.getPhone());
            if(oldUserService.save(oldUser))
                return R.success("注册成功");
            else
                return R.error("注册失败");
        }else {
            FmUser fmUser = new FmUser();
            fmUser.setName(registerRequest.getName());
            fmUser.setPassword(registerRequest.getPassword());
            fmUser.setFphone(registerRequest.getPhone());
            if(fmUserService.save(fmUser))
                return R.success("注册成功");
            else return R.success("注册失败");
        }
    }
}
