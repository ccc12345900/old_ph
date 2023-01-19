package com.zuo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuo.common.R;
import com.zuo.entity.CloudData;
import com.zuo.entity.FamilyData;
import com.zuo.entity.OldMachine;
import com.zuo.entity.OldUser;
import com.zuo.mapper.CloudDataMapper;
import com.zuo.mapper.OldMachineMapper;
import com.zuo.mapper.OldUserMapper;
import com.zuo.service.CloudDataService;
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
 * @since 2023-01-19
 */
@Service
public class CloudDataServiceImpl extends ServiceImpl<CloudDataMapper, CloudData> implements CloudDataService {
    @Autowired
    private OldMachineMapper oldMachineMapper;
    @Autowired
    private CloudDataMapper cloudDataMapper;
    @Autowired
    private OldUserMapper oldUserMapper;

    @Override
    public R getCloudData(HttpSession session) {
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

        QueryWrapper<CloudData> cloudDataQueryWrapper = new QueryWrapper<>();
        cloudDataQueryWrapper.eq("id",id);
        List<CloudData> cloudData = cloudDataMapper.selectList(cloudDataQueryWrapper);
        return R.success("查询成功",cloudData);
    }

    @Override
    public R updateCloudData(List<List<FamilyData>> family_class) {
        Integer id = family_class.get(0).get(0).getId();
        int num = cloudDataMapper.deleteById(id);
        for(List<FamilyData> t : family_class)
        {
            for(FamilyData t_s: t)
            {
                CloudData cloudData = new CloudData(t_s.getId(), t_s.getRDist(), t_s.getAngle());
                cloudDataMapper.insert(cloudData);
            }
        }
        return R.success("存储成功");
    }
}
