package com.zuo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zuo.common.R;
import com.zuo.entity.ControllUser;
import com.zuo.entity.request.ControllUserRequest;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2023-02-07
 */
public interface ControllUserService extends IService<ControllUser> {
    R verifyControlUser(ControllUserRequest controllUser, HttpSession session);
}
