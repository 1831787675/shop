package com.hxzy.service;

import com.hxzy.common.vo.ResponseMessage;
import com.hxzy.entity.GoodsType;



public interface GoodsTypeService {

    boolean deleteByPrimaryKey(Integer type_id);

    boolean insert(GoodsType record);

    GoodsType selectByPrimaryKey(Integer type_id);

    boolean updateByPrimaryKeySelective(GoodsType record);

    boolean updateByPrimaryKey(GoodsType record);

    ResponseMessage searchAll();

}
