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
 * @since 2022-12-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WatchStatus implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    private Integer status;


}
