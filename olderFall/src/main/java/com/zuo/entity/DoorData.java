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
 * @since 2023-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DoorData implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    private String k;

    private String b;

    private String r;

    private String x1;

    private String y1;

    private String x2;

    private String y2;


}
