package com.zuo.entity.request;

import lombok.Data;

/**
 * @author 橙宝cc
 * @date 2022/12/3 - 19:01
 */
@Data
public class FmUserRequest {
    private Integer fid;

    private String fphone;

    private String password;

    private String name;
}
