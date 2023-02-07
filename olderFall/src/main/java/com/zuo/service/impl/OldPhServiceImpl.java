package com.zuo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuo.common.R;
import com.zuo.entity.OldMachine;
import com.zuo.entity.OldPh;
import com.zuo.entity.request.OlderUserRequest;
import com.zuo.mapper.OldMachineMapper;
import com.zuo.mapper.OldPhMapper;
import com.zuo.service.OldPhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2022-12-07
 */
@Service
public class OldPhServiceImpl extends ServiceImpl<OldPhMapper, OldPh> implements OldPhService {
    @Autowired
    private OldMachineMapper oldMachineMapper;

    @Autowired
    private OldPhMapper oldPhMapper;
    @Override
    public R getOldPhData(Integer id) {
        //根据老人id获取设备信息
        QueryWrapper<OldMachine> familyDataQueryWrapper1 = new QueryWrapper<>();
        familyDataQueryWrapper1.eq("oid",id);
        OldMachine oldMachine = oldMachineMapper.selectOne(familyDataQueryWrapper1);
        Integer mid = oldMachine.getMid();
        //根据设备id获取手表信息
        QueryWrapper<OldPh> oldPhQueryWrapper = new QueryWrapper<>();
        oldPhQueryWrapper.eq("id",mid);
        List<OldPh> oldPhs = oldPhMapper.selectList(oldPhQueryWrapper);
        int size = oldPhs.size();
        ArrayList<OldPh> oldPhs1 = new ArrayList<>();
        int count = 0;
        for(int i = size - 1;i >= 0;i--)
        {
            if(oldPhs.get(i) != null){
                oldPhs1.add(oldPhs.get(i));
                count++;
            }else break;
            if(count == 5)break;
        }
        return R.success("获取成功",oldPhs1);
    }
}
