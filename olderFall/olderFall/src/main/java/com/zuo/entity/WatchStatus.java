package com.zuo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author admin
 * @since 2022-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WatchStatus implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    private Integer status;

    private String nettyCode;


}
