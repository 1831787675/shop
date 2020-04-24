package com.hxzy.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxzy.common.vo.BSTable;
import com.hxzy.common.vo.PageSearch;
import com.hxzy.common.vo.ResponseMessage;
import com.hxzy.entity.Delivery;
import com.hxzy.mapper.DeliveryMapper;
import com.hxzy.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private   DeliveryMapper deliveryMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean insert(Delivery record) {
        return this.deliveryMapper.insert(record)>0;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Delivery selectByPrimaryKey(Integer id) {
        return this.deliveryMapper.selectByPrimaryKey(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updateByPrimaryKeySelective(Delivery record) {
        return this.deliveryMapper.updateByPrimaryKeySelective(record)>0;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updateByPrimaryKey(Delivery record) {
        return this.deliveryMapper.updateByPrimaryKey(record)>0;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ResponseMessage search(PageSearch pageSearch) {

              //分页
        PageHelper.startPage(pageSearch.getPage(),pageSearch.getSize());

        //查询
        List<Delivery> data=this.deliveryMapper.search(pageSearch);

        Page pg=(Page) data;

        BSTable bs=new BSTable();
        bs.setTotal(pg.getTotal());
        bs.setRows(data);

        ResponseMessage  rm=ResponseMessage.success("ok",bs);

        return rm;
    }
}
