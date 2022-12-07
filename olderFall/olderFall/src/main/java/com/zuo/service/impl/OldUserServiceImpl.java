package com.zuo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zuo.common.R;
import com.zuo.entity.OldUser;
import com.zuo.entity.request.OlderUserRequest;
import com.zuo.service.OldUserService;
import com.zuo.mapper.OldUserMapper;
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
public class OldUserServiceImpl extends ServiceImpl<OldUserMapper, OldUser> implements OldUserService {

    @Autowired
    private OldUserMapper oldUserMapper;
    @Override
    public R verityPassword(OlderUserRequest olderUserRequest, HttpSession httpSession) {
        QueryWrapper<OldUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone",olderUserRequest.getPhone());
        queryWrapper.eq("password",olderUserRequest.getPassword());
        if(oldUserMapper.selectCount(queryWrapper) > 0){
            OldUser oldUser = oldUserMapper.selectOne(queryWrapper);
            httpSession.setAttribute("name",oldUser.getName());
            return R.success("登陆成功");
        }else{
            return R.error("登录失败");
        }
    }
}
