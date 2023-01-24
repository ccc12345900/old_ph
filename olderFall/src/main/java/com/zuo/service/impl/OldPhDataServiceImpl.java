package com.zuo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zuo.common.KNN;
import com.zuo.common.R;
import com.zuo.entity.OldMachine;
import com.zuo.entity.OldPhData;
import com.zuo.entity.OldUser;
import com.zuo.mapper.OldMachineMapper;
import com.zuo.mapper.OldPhDataMapper;
import com.zuo.mapper.OldUserMapper;
import com.zuo.service.OldPhDataService;
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
 * @since 2023-01-24
 */
@Service
public class OldPhDataServiceImpl extends ServiceImpl<OldPhDataMapper, OldPhData> implements OldPhDataService {

    @Autowired
    private OldPhDataMapper oldPhDataMapper;

    @Override
    public R computeData(Integer id,String heart_rate,String temp) {
        QueryWrapper<OldPhData> oldPhDataQueryWrapper = new QueryWrapper<>();
        oldPhDataQueryWrapper.eq("id",id);
        List<OldPhData> oldPhData = oldPhDataMapper.selectList(oldPhDataQueryWrapper);
        List<KNN.KnnModel> knnModelList = new ArrayList<>();
        for (OldPhData t : oldPhData)
            knnModelList.add(new KNN.KnnModel(Double.valueOf(t.getHeartRate()), 0,Double.valueOf(t.getTemp()), "正常"));
        // 预测数据
        KNN.KnnModel model = new KNN.KnnModel(Double.valueOf(heart_rate), 0, Double.valueOf(temp), null);
        // 输出预测类型
        System.out.println(KNN.calculateKnn(knnModelList, model, 3));
        return R.success(KNN.calculateKnn(knnModelList, model, 3));
    }
}
