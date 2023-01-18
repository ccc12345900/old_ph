package com.zuo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zuo.common.MStatus;
import com.zuo.common.R;
import com.zuo.entity.OldMachine;
import com.zuo.entity.OldUser;
import com.zuo.entity.RadarStatus;
import com.zuo.entity.WatchStatus;
import com.zuo.mapper.OldMachineMapper;
import com.zuo.mapper.OldUserMapper;
import com.zuo.mapper.RadarStatusMapper;
import com.zuo.mapper.WatchStatusMapper;
import com.zuo.service.RadarStatusService;
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
 * @since 2022-12-08
 */
@Service
public class RadarStatusServiceImpl extends ServiceImpl<RadarStatusMapper, RadarStatus> implements RadarStatusService {

    @Autowired
    private OldUserMapper oldUserMapper;

    @Autowired
    private OldMachineMapper oldMachineMapper;

    @Autowired
    private RadarStatusMapper radarStatusMapper;

    @Autowired
    private WatchStatusMapper watchStatusMapper;
    @Override
    public R getMachineStatus(HttpSession session) {
        //根据session查询到老人id
        Object name = session.getAttribute("name");
        QueryWrapper<OldUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",name);
        OldUser oldUser = oldUserMapper.selectOne(queryWrapper);
        //根据老人id查询设备id
        QueryWrapper<OldMachine> oldMachineQueryWrapper = new QueryWrapper<>();
        oldMachineQueryWrapper.eq("oid",oldUser.getOid());
        OldMachine oldMachine = oldMachineMapper.selectOne(oldMachineQueryWrapper);
        Integer id = oldMachine.getMid();
        //查询设备状态
        RadarStatus radarStatus = radarStatusMapper.selectById(id);
        WatchStatus watchStatus = watchStatusMapper.selectById(id);
        MStatus mStatus = new MStatus();
        mStatus.setRadarStatus(radarStatus.getStatus());
        mStatus.setWatchStatus(watchStatus.getStatus());
        //返回设备状态
        return R.success("查询成功",mStatus);
    }
}
