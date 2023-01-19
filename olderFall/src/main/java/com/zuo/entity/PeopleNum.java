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
 * @since 2023-01-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PeopleNum implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    private Integer peopleNum;


}
