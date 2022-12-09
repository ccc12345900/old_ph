package com.zuo.service;

import com.zuo.common.R;
import com.zuo.entity.RadarStatus;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2022-12-08
 */
public interface RadarStatusService extends IService<RadarStatus> {
    R getMachineStatus(HttpSession session);
}
