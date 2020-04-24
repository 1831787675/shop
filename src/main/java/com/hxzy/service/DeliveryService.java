package com.hxzy.service;

import com.hxzy.common.vo.PageSearch;
import com.hxzy.common.vo.ResponseMessage;
import com.hxzy.entity.Delivery;

import java.util.List;

public interface DeliveryService {
    boolean insert(Delivery record);

    Delivery selectByPrimaryKey(Integer id);

    boolean updateByPrimaryKeySelective(Delivery record);

    boolean updateByPrimaryKey(Delivery record);

    ResponseMessage search(PageSearch search);
}
