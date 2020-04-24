package com.hxzy.controller.admin;

import com.hxzy.common.vo.PageSearch;
import com.hxzy.common.vo.ResponseMessage;
import com.hxzy.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin/delivery")
public class AdminDeliveryController {
    @Autowired
    private DeliveryService deliveryService;

    @GetMapping(value="/data")
    public ResponseMessage ajaxData(PageSearch search){
        return this.deliveryService.search(search);
    }
}
