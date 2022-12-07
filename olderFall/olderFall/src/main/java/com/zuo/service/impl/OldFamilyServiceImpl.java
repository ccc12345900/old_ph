package com.zuo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zuo.common.R;
import com.zuo.entity.FmUser;
import com.zuo.entity.OldFamily;
import com.zuo.entity.OldUser;
import com.zuo.entity.request.FmUserRequest;
import com.zuo.mapper.FmUserMapper;
import com.zuo.service.OldFamilyService;
import com.zuo.mapper.OldFamilyMapper;
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
public class OldFamilyServiceImpl extends ServiceImpl<OldFamilyMapper, OldFamily> implements OldFamilyService {
    @Autowired
    private FmUserMapper fmUserMapper;//家属信息

    @Autowired
    private OldUserMapper oldUserMapper;//老人信息

    @Autowired
    private OldFamilyMapper oldFamilyMapper;//关联信息
    @Override
    public R getFamilyMessage(HttpSession session) {
        String name = (String) session.getAttribute("name");
        Boolean family = (Boolean) session.getAttribute("family");
        if(family == null)//如果为空说明是老人，查询家属信息
        {
            //先查询到老人的id
            QueryWrapper<OldUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name",name);
            OldUser oldUser = oldUserMapper.selectOne(queryWrapper);
            //然后根据关联表查询到老人家属id
            QueryWrapper<OldFamily> wrapper = new QueryWrapper<>();
            wrapper.eq("oid",oldUser.getOid());
            List<OldFamily> oldFamilies = oldFamilyMapper.selectList(wrapper);
            //通过家属id获取到家属的信息
            if(oldFamilies!=null){
                List<FmUser> list = new ArrayList<>();
                for (OldFamily t : oldFamilies){
                    list.add(fmUserMapper.selectById(t.getFid()));
                }
                return R.success("查询成功",list);
            }else{
                return R.error("查询失败");
            }
        }
        else {//如果是家属查询老人信息
            //先查询到家属id
            QueryWrapper<FmUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name",name);
            FmUser fmUser = fmUserMapper.selectOne(queryWrapper);
            //然后根据关联表查询到老人的id
            QueryWrapper<OldFamily> wrapper = new QueryWrapper<>();
            wrapper.eq("fid",fmUser.getFid());
            List<OldFamily> oldFamilies = oldFamilyMapper.selectList(wrapper);
            //根据老人id获取到老人的信息
            if(oldFamilies != null){
                List<OldUser> list = new ArrayList<>();
                for (OldFamily t : oldFamilies){
                    list.add(oldUserMapper.selectById(t.getOid()));
                }
                return R.success("查询成功",list);
            }else{
                return R.error("查询失败");
            }
        }
    }

    @Override
    public R bindFamily(FmUserRequest fmUserRequest, HttpSession session) {
        QueryWrapper<FmUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fphone",fmUserRequest.getFphone());
        queryWrapper.eq("name",fmUserRequest.getName());
        FmUser fmUser = fmUserMapper.selectOne(queryWrapper);
        if(fmUser!=null){
            QueryWrapper<OldUser> queryWrapper1 = new QueryWrapper<>();
            QueryWrapper<OldFamily> queryWrapper2 = new QueryWrapper<>();
            Object name = session.getAttribute("name");
            if(name == null)return R.error("请登陆后再试");

            queryWrapper1.eq("name",name);
            OldUser oldUser = oldUserMapper.selectOne(queryWrapper1);
            queryWrapper2.eq("oid",oldUser.getOid());
            queryWrapper2.eq("fid",fmUser.getFid());
            if(oldFamilyMapper.selectList(queryWrapper2).size() != 0)return R.error("请勿重复添加");

            OldFamily oldFamily = new OldFamily();
            oldFamily.setOid(oldUser.getOid());
            oldFamily.setFid(fmUser.getFid());
            oldFamilyMapper.insert(oldFamily);
            return R.success("绑定成功");
        }else {
            return R.error("未查询到该账号，请确认后再绑定");
        }
    }

    @Override
    public R untieFamily(FmUserRequest fmUserRequest, HttpSession session) {
        Object name = session.getAttribute("name");
        if(name == null)return R.error("请登陆后再试");
        //先获取老人信息
        QueryWrapper<OldUser> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name",name);
        OldUser oldUser = oldUserMapper.selectOne(queryWrapper1);
        //在获取家属信息
        QueryWrapper<FmUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fphone",fmUserRequest.getFphone());
        queryWrapper.eq("name",fmUserRequest.getName());
        FmUser fmUser = fmUserMapper.selectOne(queryWrapper);

        //解除绑定
        QueryWrapper<OldFamily> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("oid",oldUser.getOid());
        queryWrapper2.eq("fid",fmUser.getFid());
        oldFamilyMapper.delete(queryWrapper2);
        return R.success("删除成功");
    }


}
