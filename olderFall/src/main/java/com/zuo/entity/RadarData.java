package com.zuo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author admin
 * @since 2022-12-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RadarData implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    private Double rDist;

    private String angle;

    private LocalDateTime time;


}
