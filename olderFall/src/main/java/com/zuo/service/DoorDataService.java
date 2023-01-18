package com.zuo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zuo.common.R;
import com.zuo.entity.DoorData;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2023-01-18
 */
public interface DoorDataService extends IService<DoorData> {
    R getDoor(HttpSession session);
    R createDoor(HttpSession session);

    DoorData getDoorById(Integer id);
}
