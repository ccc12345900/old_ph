package com.zuo.service;

import com.zuo.common.R;
import com.zuo.entity.FmUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zuo.entity.request.FmUserRequest;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2022-12-03
 */
public interface FmUserService extends IService<FmUser> {
    R verfyFmUser(FmUserRequest fmUserRequest, HttpSession httpSession);

    R familyPeopleNum(HttpSession session);

    R deleteFmUser(FmUserRequest fmUserRequest,HttpSession session);

    R getOldList(Integer id);
}
