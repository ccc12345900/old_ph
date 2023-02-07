package com.zuo.entity.request;

import lombok.Data;

/**
 * @author 橙宝cc
 * @date 2023/2/7 - 16:01
 */
@Data
public class ControllUserRequest {
    private Integer cId;

    private String cphone;

    private String cpassword;

    private String cName;
}
