package com.zuo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zuo.entity.ControllUser;
import com.zuo.entity.OldFamily;
import com.zuo.entity.OldUser;
import com.zuo.mapper.ControllUserMapper;
import com.zuo.mapper.OldFamilyMapper;
import com.zuo.mapper.OldUserMapper;
import com.zuo.service.FmUserService;
import com.zuo.common.R;
import com.zuo.entity.FmUser;
import com.zuo.entity.request.FmUserRequest;
import com.zuo.mapper.FmUserMapper;
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
public class FmUserServiceImpl extends ServiceImpl<FmUserMapper, FmUser> implements FmUserService {

    @Autowired
    private FmUserMapper fmUserMapper;

    @Autowired
    private ControllUserMapper controllUserMapper;

    @Autowired
    private OldFamilyMapper oldFamilyMapper;

    @Autowired
    private OldUserMapper oldUserMapper;
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

    @Override
    public R familyPeopleNum(HttpSession session) {
        QueryWrapper<ControllUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("c_name",session.getAttribute("c_name"));
        if(controllUserMapper.selectCount(queryWrapper) > 0){
            List<FmUser> list = fmUserMapper.selectList(null);
            return R.success("查询成功",list);
        }
        return R.error("查询失败");
    }

    @Override
    public R deleteFmUser(FmUserRequest fmUserRequest,HttpSession session) {
        QueryWrapper<ControllUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("c_name",session.getAttribute("c_name"));
        if(controllUserMapper.selectCount(queryWrapper) > 0){
            //先和老人解除绑定
            QueryWrapper<OldFamily> oldFamilyQueryWrapper = new QueryWrapper<>();
            oldFamilyQueryWrapper.eq("fid",fmUserRequest.getFid());
            oldFamilyMapper.delete(oldFamilyQueryWrapper);
            //然后删除家属
            QueryWrapper<FmUser> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("fid",fmUserRequest.getFid());
            fmUserMapper.delete(queryWrapper1);
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

    @Override
    public R getOldList(Integer id) {
        //根据家属id获取老人id
        QueryWrapper<OldFamily> oldFamilyQueryWrapper = new QueryWrapper<>();
        oldFamilyQueryWrapper.eq("fid",id);
        List<OldFamily> oldFamilies = oldFamilyMapper.selectList(oldFamilyQueryWrapper);
        ArrayList<OldUser> oldUsers = new ArrayList<>();
        for(OldFamily t : oldFamilies){
            OldUser oldUser = new OldUser();
            oldUser.setOid(t.getOid());
            OldUser oldUser1 = oldUserMapper.selectById(oldUser);
            oldUsers.add(oldUser1);
        }
        return R.success("获取成功",oldUsers);
    }
}
