package com.zuo.entity.request;

import lombok.Data;

/**
 * @author 橙宝cc
 * @date 2022/12/3 - 17:03
 */
@Data
public class OlderUserRequest {
    private Integer oid;

    private String phone;

    private String password;

    private String name;

}
