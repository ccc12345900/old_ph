package com.zuo.entity;

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
public class OldFamily implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer oid;

    private Integer fid;


}
