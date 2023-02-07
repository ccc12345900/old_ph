package com.zuo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zuo.common.R;
import com.zuo.entity.ControllUser;
import com.zuo.entity.FmUser;
import com.zuo.entity.OldFamily;
import com.zuo.entity.OldUser;
import com.zuo.entity.request.OlderUserRequest;
import com.zuo.mapper.ControllUserMapper;
import com.zuo.mapper.FmUserMapper;
import com.zuo.mapper.OldFamilyMapper;
import com.zuo.service.OldUserService;
import com.zuo.mapper.OldUserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private ControllUserMapper controllUserMapper;

    @Autowired
    private OldFamilyMapper oldFamilyMapper;

    @Autowired
    private FmUserMapper fmUserMapper;
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

    @Override
    public R OldPeopleNumber(HttpSession session) {
        QueryWrapper<ControllUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("c_name",session.getAttribute("c_name"));
        if(controllUserMapper.selectCount(queryWrapper) > 0){
            List<OldUser> oldUsers = oldUserMapper.selectList(null);
            return R.success("查询成功",oldUsers);
        }
        return R.error("查询失败");
    }

    @Override
    public R deleteOldUser(OlderUserRequest olderUserRequest, HttpSession session) {
        QueryWrapper<ControllUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("c_name",session.getAttribute("c_name"));
        if(controllUserMapper.selectCount(queryWrapper) > 0){
            //先和家属解除绑定
            QueryWrapper<OldFamily> oldFamilyQueryWrapper = new QueryWrapper<>();
            oldFamilyQueryWrapper.eq("oid",olderUserRequest.getOid());
            oldFamilyMapper.delete(oldFamilyQueryWrapper);
            //然后删除老人账号
            QueryWrapper<OldUser> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("oid",olderUserRequest.getOid());
            oldUserMapper.delete(queryWrapper1);
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

    @Override
    public R getFamilyList(Integer id) {
        //根据老人id获取家属id
        QueryWrapper<OldFamily> oldFamilyQueryWrapper = new QueryWrapper<>();
        oldFamilyQueryWrapper.eq("oid",id);
        List<OldFamily> oldFamilies = oldFamilyMapper.selectList(oldFamilyQueryWrapper);
        ArrayList<FmUser> fmUsers = new ArrayList<>();
        for(OldFamily t : oldFamilies){
            FmUser fmUser1 = new FmUser();
            fmUser1.setFid(t.getFid());
            FmUser fmUser = fmUserMapper.selectById(fmUser1);
            fmUsers.add(fmUser);
        }
        return R.success("获取成功",fmUsers);
    }
}
