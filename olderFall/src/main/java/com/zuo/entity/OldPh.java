package com.zuo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class OldPh implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    private String heartRate;

    private String bloodPressure;

    private String temperature;

    private Integer fall;

    private LocalDateTime time;


}
