package com.hxzy.service.impl;

import com.hxzy.common.vo.ResponseMessage;
import com.hxzy.common.vo.TreeGoodsVO;
import com.hxzy.entity.GoodsType;
import com.hxzy.mapper.GoodsTypeMapper;
import com.hxzy.service.GoodsTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GoodsTypeServiceImpl implements GoodsTypeService {

    @Autowired
    private GoodsTypeMapper goodsTypeMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean deleteByPrimaryKey(Integer type_id) {
        return this.goodsTypeMapper.deleteByPrimaryKey(type_id)>0;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean insert(GoodsType record) {
        return this.goodsTypeMapper.insert(record)>0;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public GoodsType selectByPrimaryKey(Integer type_id) {
        return this.goodsTypeMapper.selectByPrimaryKey(type_id);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updateByPrimaryKeySelective(GoodsType record) {
        return this.goodsTypeMapper.updateByPrimaryKeySelective(record)>0;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updateByPrimaryKey(GoodsType record) {
        return this.goodsTypeMapper.updateByPrimaryKey(record)>0;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ResponseMessage searchAll() {
        List<GoodsType> dbGoodsTypes = this.goodsTypeMapper.searchAll();
        List<TreeGoodsVO> allTree = new ArrayList<>();

        dbGoodsTypes.stream().filter(p->p.getParentId()==0).forEach(p->{
            TreeGoodsVO treeGoodsVO = new TreeGoodsVO();

            BeanUtils.copyProperties(p,treeGoodsVO);

            getChildren(dbGoodsTypes, treeGoodsVO);

            allTree.add(treeGoodsVO);
        });
        return ResponseMessage.success("ok",allTree);
    }

    private void getChildren(List<GoodsType> dbGoodsTypes, TreeGoodsVO treeGoodsVO) {
        List<TreeGoodsVO> childrenList = new ArrayList<>();
        dbGoodsTypes.stream().filter(z->z.getParentId()==treeGoodsVO.getId()).forEach(c->{
            TreeGoodsVO child = new TreeGoodsVO();
            BeanUtils.copyProperties(c,child);



            getChildren( dbGoodsTypes,child);

            childrenList.add(child);

        });
        if(childrenList.size()>0){
            treeGoodsVO.setChildren(childrenList);
        }
    }
}
