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
 * @since 2023-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ControllUser implements Serializable {

    private static final long serialVersionUID=1L;

      private Integer cId;

    private String cPhone;

    private String cPassword;

    private String cName;


}
