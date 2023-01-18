package com.zuo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuo.common.R;
import com.zuo.entity.DoorData;
import com.zuo.entity.FamilyData;
import com.zuo.entity.OldMachine;
import com.zuo.entity.OldUser;
import com.zuo.mapper.DoorDataMapper;
import com.zuo.mapper.OldMachineMapper;
import com.zuo.mapper.OldUserMapper;
import com.zuo.service.DoorDataService;
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
 * @since 2023-01-18
 */
@Service
public class DoorDataServiceImpl extends ServiceImpl<DoorDataMapper, DoorData> implements DoorDataService {
    @Autowired
    private OldMachineMapper oldMachineMapper;
    @Autowired
    private FamilyDataService familyDataService;

    @Autowired
    private DoorDataMapper doorDataMapper;

    @Autowired
    private OldUserMapper oldUserMapper;
    @Override
    public R getDoor(HttpSession session) {
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
        //根据id获取门的信息
        QueryWrapper<DoorData> doorDataQueryWrapper = new QueryWrapper<>();
        doorDataQueryWrapper.eq("id",id);
        DoorData doorData = doorDataMapper.selectOne(doorDataQueryWrapper);
        if(doorData == null)
            return R.error("查询失败");
        return R.success("查询成功",doorData);
    }

    @Override
    public R createDoor(HttpSession session) {
        //判断是否有门的信息,如果有就删除
        R door = this.getDoor(session);
        if(door.getData() != null)
        {
            DoorData data = (DoorData) door.getData();
            doorDataMapper.deleteById(data.getId());
        }
        //获取家庭环境信息
        R familyData = familyDataService.getFamilyData(session);
        Object data = familyData.getData();
        List<FamilyData> family_data = (List<FamilyData>) data;


        for(int i = 1,j = 0;i < family_data.size();i++,j++)
        {
            String a1 = family_data.get(i).getAngle();
            String a2 = family_data.get(j).getAngle();
            Double a1_angle = new Double(a1);
            Double a2_angle = new Double(a2);
            if(Math.abs(a1_angle - a2_angle) >= 10)
            {
                Double a1_r = family_data.get(i).getRDist();
                Double a2_r = family_data.get(j).getRDist();
                Double y1 = a1_r * Math.cos(a1_angle * (Math.PI / 180));//x1坐标
                Double x1 = a1_r * Math.sin(a1_angle * (Math.PI / 180));//y1坐标
                Double y2 = a2_r * Math.cos(a2_angle * (Math.PI / 180));//x2坐标
                Double x2 = a2_r * Math.sin(a2_angle * (Math.PI / 180));//y2坐标
                Double k = (y2 - y1)/(x2 - x1);
                Double b = y1 - (k * x1);
                Double r = Math.sqrt(Math.pow((x1-x2),2) + Math.pow((y1-y2),2));
                DoorData doorData = new DoorData();
                doorData.setB(String.valueOf(b));
                doorData.setK(String.valueOf(k));
                doorData.setId(family_data.get(i).getId());
                doorData.setR(String.valueOf(r));
                doorData.setX1(String.valueOf(x1));
                doorData.setY1(String.valueOf(y1));
                doorData.setX2(String.valueOf(x2));
                doorData.setY2(String.valueOf(y2));
                doorDataMapper.insert(doorData);
                return R.success("门的位置已确定");
            }
        }
        return R.error("未检测到门");
    }

    @Override
    public DoorData getDoorById(Integer id) {
        //根据id获取门的信息
        QueryWrapper<DoorData> doorDataQueryWrapper = new QueryWrapper<>();
        doorDataQueryWrapper.eq("id",id);
        DoorData doorData = doorDataMapper.selectOne(doorDataQueryWrapper);
        if(doorData == null)
            return null;
        return doorData;
    }
}
