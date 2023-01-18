package com.zuo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zuo.common.R;
import com.zuo.entity.FamilyData;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2023-01-16
 */
public interface FamilyDataService extends IService<FamilyData> {
    boolean updateFamily(List<FamilyData> familyList);
    R getFamilyData(HttpSession session);

    List<FamilyData> getFamilyDataById(Integer id);
}
