package com.hxzy.mapper;

import com.hxzy.common.vo.PageSearch;
import com.hxzy.entity.Delivery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeliveryMapper {

    int insert(Delivery record);

    Delivery selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Delivery record);

    int updateByPrimaryKey(Delivery record);

    List<Delivery> search(PageSearch search);
}