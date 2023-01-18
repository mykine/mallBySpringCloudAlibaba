package cn.mykine.mall.order.mapper;


import cn.mykine.mall.order.model.OrderItemDO;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderItemMapper {

    /**
     * 新增订单明细
     *
     * @param list
     * @return
     */
    int insertOrderItems(List<OrderItemDO> list);

    List<OrderItemDO> itemList(Long orderNo);
}
