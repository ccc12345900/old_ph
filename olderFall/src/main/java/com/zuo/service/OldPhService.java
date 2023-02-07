package com.zuo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zuo.common.R;
import com.zuo.entity.OldPh;
import com.zuo.entity.request.OlderUserRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2022-12-07
 */
public interface OldPhService extends IService<OldPh> {
    R getOldPhData(Integer id);
}
