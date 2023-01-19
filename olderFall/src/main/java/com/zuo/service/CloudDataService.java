package com.zuo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zuo.common.R;
import com.zuo.entity.CloudData;
import com.zuo.entity.FamilyData;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2023-01-19
 */
public interface CloudDataService extends IService<CloudData> {
    R getCloudData(HttpSession session);
    R updateCloudData(List<List<FamilyData>> family_class);
}
