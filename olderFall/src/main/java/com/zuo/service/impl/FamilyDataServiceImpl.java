package com.zuo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuo.common.R;
import com.zuo.entity.FamilyData;
import com.zuo.entity.OldMachine;
import com.zuo.entity.OldUser;
import com.zuo.mapper.FamilyDataMapper;
import com.zuo.mapper.OldMachineMapper;
import com.zuo.mapper.OldUserMapper;
import com.zuo.service.FamilyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2023-01-16
 */
@Service
public class FamilyDataServiceImpl extends ServiceImpl<FamilyDataMapper, FamilyData> implements FamilyDataService {

    @Autowired
    private OldMachineMapper oldMachineMapper;

    @Autowired
    private FamilyDataMapper familyDataMapper;

    @Autowired
    private OldUserMapper oldUserMapper;
    @Override
    public boolean updateFamily(List<FamilyData> familyList) {
        //先判断是否数据库中有之前的数据
        //删除数据
        FamilyData familyData = familyList.get(0);
        Integer id = familyData.getId();
        int deleteNum = familyDataMapper.deleteById(id);
        int insertNum = 0;
        //重新存储
        for(FamilyData t : familyList)
        {
            int insert = familyDataMapper.insert(t);
            if(insert == 0) insertNum++;
        }
        if(insertNum != familyList.size())return true;
        return false;
    }

    @Override
    public R getFamilyData(HttpSession session) {
        //获取老人信息
        Object name = session.getAttribute("name");
        QueryWrapper<OldUser> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name",name);
        OldUser oldUser = oldUserMapper.selectOne(queryWrapper1);
        //根据老人id查询设备id
        QueryWrapper<OldMachine> oldMachineQueryWrapper = new QueryWrapper<>();
        oldMachineQueryWrapper.eq("oid",oldUser.getOid());
        OldMachine oldMachine = oldMachineMapper.selectOne(oldMachineQueryWrapper);
        Integer id = oldMachine.getMid();
        //获取数据
        QueryWrapper<FamilyData> familyDataQueryWrapper = new QueryWrapper<>();
        familyDataQueryWrapper.eq("id",id);
        List<FamilyData> familyData = familyDataMapper.selectList(familyDataQueryWrapper);
        return R.success("查询成功",familyData);
    }

    @Override
    public List<FamilyData> getFamilyDataById(Integer id) {
        QueryWrapper<FamilyData> familyDataQueryWrapper = new QueryWrapper<>();
        familyDataQueryWrapper.eq("id",id);
        List<FamilyData> familyData = familyDataMapper.selectList(familyDataQueryWrapper);
        return familyData;
    }
}
