package com.hxzy.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * rw_goods_type
 * @author 
 */
@Data
public class GoodsType implements Serializable {
    /**
     * 商品分类唯一编码
     */
    private Integer id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 商品分类图片url
     */
    private String img;

    /**
     * 排序值（升序排列）
     */
    private Integer sort;

    /**
     * 父分类id
     */
    private Integer parentId;

    /**
     * 是否显示在导航栏
     */
    private Boolean showNav;

    /**
     * 是否显示在主页
     */
    private Boolean showIndex;

    /**
     * 预留字段
     */
    private String value1;

    /**
     * 预留字段
     */
    private String value2;

    /**
     * 预留字段
     */
    private String value3;

    private String parentName;

    private static final long serialVersionUID = 1L;
}