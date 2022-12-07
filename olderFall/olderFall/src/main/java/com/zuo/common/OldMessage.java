package com.zuo.common;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zuo.entity.FmUser;
import com.zuo.entity.OldFamily;
import com.zuo.entity.OldPh;
import com.zuo.entity.OldUser;
import com.zuo.mapper.FmUserMapper;
import com.zuo.mapper.OldFamilyMapper;
import com.zuo.mapper.OldUserMapper;
import com.zuo.service.OldPhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 橙宝cc
 * @date 2022/12/7 - 17:16
 */
@Component
public class OldMessage {

    private static FmUserMapper fmUserMapper;//家属信息

    @Autowired
    public void setFmUserMapper(FmUserMapper fmUserMapper){
        OldMessage.fmUserMapper = fmUserMapper;
    }

    private static OldUserMapper oldUserMapper;//老人信息

    @Autowired
    public void setOldUserMapper(OldUserMapper oldUserMapper){
        OldMessage.oldUserMapper = oldUserMapper;
    }
    private static OldFamilyMapper oldFamilyMapper;//关联信息

    @Autowired
    public void setOldFamilyMapper(OldFamilyMapper oldFamilyMapper){
        OldMessage.oldFamilyMapper = oldFamilyMapper;
    }

    private static OldPhService oldPhService;//老人身体数据

    @Autowired
    public void setOldPhService(OldPhService oldPhService){
        OldMessage.oldPhService = oldPhService;
    }

    public JSONObject getOldMessage(HttpSession session){
        JSONObject jsonObject = new JSONObject();
        String name = (String) session.getAttribute("name");
        Boolean family = (Boolean) session.getAttribute("family");
        if(family == null)//如果为空说明是老人
        {
            //先查询到老人的id
            QueryWrapper<OldUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name",name);
            OldUser oldUser = oldUserMapper.selectOne(queryWrapper);
            QueryWrapper<OldPh> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("id",oldUser.getOid());
            List<OldPh> list = oldPhService.list(queryWrapper1);
            jsonObject.put("old_ph",list);
            return jsonObject;
        }
        else {
            //如果是家属查询老人信息
            //先查询到家属id
            QueryWrapper<FmUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name",name);
            FmUser fmUser = fmUserMapper.selectOne(queryWrapper);
            //然后根据关联表查询到老人的id
            QueryWrapper<OldFamily> wrapper = new QueryWrapper<>();
            wrapper.eq("fid",fmUser.getFid());
            List<OldFamily> oldFamilies = oldFamilyMapper.selectList(wrapper);
            //根据老人id获取到老人的信息
            if(oldFamilies != null) {
                List<List<OldPh>> list = new ArrayList<>();
                for (OldFamily t : oldFamilies) {
                    QueryWrapper<OldPh> queryWrapper1 = new QueryWrapper<>();
                    queryWrapper1.eq("id", t.getOid());
                    list.add(oldPhService.list(queryWrapper1));
                }
                jsonObject.put("old_ph", list);
                return jsonObject;
            }
        }
        return jsonObject;
    }
}
