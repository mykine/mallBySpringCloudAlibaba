package cn.mykine.mall.order.mapper;


import cn.mykine.mall.common.dto.OrderDTO;
import cn.mykine.mall.common.dto.PageQueryOrdersDTO;
import cn.mykine.mall.order.model.OrderDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    /**
     * 新增订单
     *
     * @param orderDO
     * @return
     */
    int insertOrder(OrderDO orderDO);

    List<OrderDO> orderList(PageQueryOrdersDTO param);

}
