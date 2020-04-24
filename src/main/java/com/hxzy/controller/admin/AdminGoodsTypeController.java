package com.hxzy.controller.admin;

import com.hxzy.common.vo.ResponseMessage;
import com.hxzy.service.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin/goods")
public class AdminGoodsTypeController {
    @Autowired
    private GoodsTypeService goodsTypeService;
    @GetMapping(value = "/data")
    public ResponseMessage getDate(){
        return  this.goodsTypeService.searchAll();
    }


}
