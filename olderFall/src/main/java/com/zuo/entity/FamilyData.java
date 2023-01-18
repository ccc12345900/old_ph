package com.zuo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author admin
 * @since 2023-01-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FamilyData implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    private Double rDist;

    private String angle;


}
