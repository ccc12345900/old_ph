package com.zuo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuo.common.R;
import com.zuo.entity.ControllUser;
import com.zuo.entity.FmUser;
import com.zuo.entity.request.ControllUserRequest;
import com.zuo.mapper.ControllUserMapper;
import com.zuo.service.ControllUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2023-02-07
 */
@Service
public class ControllUserServiceImpl extends ServiceImpl<ControllUserMapper, ControllUser> implements ControllUserService {

    @Autowired
    private ControllUserMapper controllUserMapper;

    @Override
    public R verifyControlUser(ControllUserRequest controllUser, HttpSession session) {
        QueryWrapper<ControllUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("c_phone",controllUser.getCphone());
        queryWrapper.eq("c_password",controllUser.getCpassword());
        if(controllUserMapper.selectCount(queryWrapper) > 0){
            ControllUser controllUser1 = controllUserMapper.selectOne(queryWrapper);
            session.setAttribute("c_name",controllUser1.getCName());
            return R.success("登陆成功");
        }else {
            return R.error("登陆失败");
        }
    }
}
