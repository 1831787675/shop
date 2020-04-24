package com.hxzy.mapper;

import com.hxzy.entity.GoodsType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GoodsTypeMapper {
    int deleteByPrimaryKey(Integer type_id);

    int insert(GoodsType record);

    GoodsType selectByPrimaryKey(Integer type_id);

    int updateByPrimaryKeySelective(GoodsType record);

    int updateByPrimaryKey(GoodsType record);

    List<GoodsType> searchAll();
}