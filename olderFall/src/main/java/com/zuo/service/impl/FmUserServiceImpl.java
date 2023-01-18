package com.zuo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zuo.service.FmUserService;
import com.zuo.common.R;
import com.zuo.entity.FmUser;
import com.zuo.entity.request.FmUserRequest;
import com.zuo.mapper.FmUserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2022-12-03
 */
@Service
public class FmUserServiceImpl extends ServiceImpl<FmUserMapper, FmUser> implements FmUserService {

    @Autowired
    private FmUserMapper fmUserMapper;

    @Override
    public R verfyFmUser(FmUserRequest fmUserRequest, HttpSession httpSession) {
        QueryWrapper<FmUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fphone",fmUserRequest.getFphone());
        queryWrapper.eq("password",fmUserRequest.getPassword());
        if(fmUserMapper.selectCount(queryWrapper) > 0){
            FmUser fmUser = fmUserMapper.selectOne(queryWrapper);
            httpSession.setAttribute("name",fmUser.getName());
            httpSession.setAttribute("family",true);
            return R.success("登陆成功");
        }else {
            return R.error("登陆失败");
        }
    }
}
