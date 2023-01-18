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
public class FmUser implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "fid", type = IdType.AUTO)
    private Integer fid;

    private String fphone;

    private String password;

    private String name;


}
