package com.shengdingbox.blog.persistence.beans;

import javax.persistence.Transient;

import com.shengdingbox.blog.framework.object.AbstractDO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author Dabao (17611555590@163.com)
 * @version 1.0
 * @website https://www.shengdingbox.com
 * @date 2019年7月16日
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRole extends AbstractDO {
    private String name;
    private String description;
    private Boolean available;

    @Transient
    private Integer selected;
}
