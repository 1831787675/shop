package com.hxzy.common.vo;

import lombok.*;

/**
 * 分页查询通用类(接收参数)
 */
@Getter
@Setter
public class PageSearch {
    /**
     * 当前第几页
     */
    private int page;

    /**
     * 每页显示几笔
     */
    private int size;

}
