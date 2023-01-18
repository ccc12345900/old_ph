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
public class OldPh implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    private String heartRate;

    private String bloodPressure;

    private String temperature;

    private Integer fall;


}
