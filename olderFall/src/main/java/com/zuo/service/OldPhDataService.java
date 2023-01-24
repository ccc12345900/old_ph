package com.zuo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zuo.common.R;
import com.zuo.entity.OldPhData;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2023-01-24
 */
public interface OldPhDataService extends IService<OldPhData> {
    R computeData(Integer id,String heart_rate,String temp);
}
