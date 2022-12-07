package com.zuo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author admin
 * @since 2022-12-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OldUser implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "oid", type = IdType.AUTO)
    private Integer oid;

    private String phone;

    private String password;

    private String name;


}
