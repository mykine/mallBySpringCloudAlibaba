package cn.mykine.mall.order.service.impl;

import cn.mykine.mall.order.mapper.OrderItemMapper;
import cn.mykine.mall.order.service.IOrderItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class OrderItemServiceImpl implements IOrderItemService {

    @Resource
    OrderItemMapper orderItemMapper;

}
