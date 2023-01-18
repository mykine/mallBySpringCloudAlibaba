package cn.mykine.mall.order.service;


import cn.mykine.mall.common.dto.OrderDTO;
import cn.mykine.mall.common.dto.PageQueryOrdersDTO;

import java.util.List;

/**
 * 订单服务Service
 */
public interface IOrderService {

    /**
     * 创建订单
     *
     * @param orderDTO
     * @return
     */
    boolean createOrder(OrderDTO orderDTO);

    List<OrderDTO> orderList(PageQueryOrdersDTO param);

}
