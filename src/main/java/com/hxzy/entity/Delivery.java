package com.hxzy.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * 快递表
 * @author 
 */
@Data
public class Delivery implements Serializable {
    /**
     * 配送方式UID
     */
    private Integer id;

    /**
     * 配送说明
     */
    private String desc;

    /**
     * 配送费
     */
    private BigDecimal price;

    /**
     * 状态：0代表禁用，1代表启用
     */
    private Integer state;

    private static final long serialVersionUID = 1L;
}