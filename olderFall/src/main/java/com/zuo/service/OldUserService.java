package com.zuo.service;

import com.zuo.common.R;
import com.zuo.entity.OldUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zuo.entity.request.OlderUserRequest;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2022-12-03
 */
public interface OldUserService extends IService<OldUser> {
    R verityPassword(OlderUserRequest olderUserRequest, HttpSession httpSession);
    R OldPeopleNumber(HttpSession session);

    R deleteOldUser(OlderUserRequest olderUserRequest,HttpSession session);

    R getFamilyList(Integer id);
}
