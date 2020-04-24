package com.hxzy.common.vo;

import com.hxzy.entity.GoodsType;
import lombok.Data;

import java.util.List;

@Data
public class TreeGoodsVO extends GoodsType {

    private List<TreeGoodsVO> children;

}
