package com.zuo.entity.request;

import lombok.Data;

/**
 * @author 橙宝cc
 * @date 2022/12/7 - 18:09
 */
@Data
public class RegisterRequest {
    private String phone;

    private String password;

    private String name;

    private Integer type;//1为老人，2为家属
}
