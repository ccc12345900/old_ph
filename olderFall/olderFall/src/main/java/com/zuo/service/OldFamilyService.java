package com.zuo.service;

import com.zuo.common.R;
import com.zuo.entity.OldFamily;
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
public interface OldFamilyService extends IService<OldFamily> {
    R getFamilyMessage(HttpSession session);

    R bindFamily(FmUserRequest fmUserRequest,HttpSession session);

    R untieFamily(FmUserRequest fmUserRequest,HttpSession session);
}
