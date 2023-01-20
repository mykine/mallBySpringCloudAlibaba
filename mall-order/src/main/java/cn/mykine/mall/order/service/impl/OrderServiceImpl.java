package cn.mykine.mall.order.service.impl;


import cn.mykine.mall.common.base.BaseResponse;
import cn.mykine.mall.common.dto.*;
import cn.mykine.mall.common.util.Assert;
import cn.mykine.mall.common.util.JSONUtil;
import cn.mykine.mall.common.util.ObjectTransformer;
import cn.mykine.mall.order.feign.coupon.CouponFeign;
import cn.mykine.mall.order.feign.goods.GoodsFeign;
import cn.mykine.mall.order.feign.user.UserFeign;
import cn.mykine.mall.order.mapper.OrderItemMapper;
import cn.mykine.mall.order.mapper.OrderMapper;
import cn.mykine.mall.order.model.OrderDO;
import cn.mykine.mall.order.model.OrderItemDO;
import cn.mykine.mall.order.service.IOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {

    @Resource
    OrderMapper orderMapper;

    @Resource
    OrderItemMapper orderItemMapper;

    @Resource
    CouponFeign couponFeign;

    @Resource
    GoodsFeign goodsFeign;

    @Resource
    UserFeign userFeign;

    /**
     * 下单服务
     * <p>
     * 1、先判断是否有使用优惠券，有的话则调用优惠券服务，进行优惠券核销处理 <br/>
     * 2、获取订单明细列表，调用商品服务，根据商品ID和购买数量，进行库存检查和扣减 <br/>
     * 3、保存主订单信息 <br/>
     * 4、保存订单明细 <br/>
     * </p>
     *
     * @param orderDTO
     * @return
     */
    @Override
    public boolean createOrder(OrderDTO orderDTO) {

        /** 1、优惠券处理 **/
        if (orderDTO.getCouponRecordId() != null) {
            CouponRecordDTO couponRecordDTO = new CouponRecordDTO.Builder()
                    .id(orderDTO.getCouponRecordId())
                    .userId(orderDTO.getUserId())
                    .build();
            BaseResponse useCouponResponse = couponFeign.useCoupon(couponRecordDTO);
            Assert.successResponse(useCouponResponse);
        }

        /** 2、检查并且扣减库存 **/
        List<OrderItemDTO> orderItemDTOList = orderDTO.getOrderItemDTOList();
        BaseResponse stockResponse = goodsFeign.checkAndDecreaseStock(orderItemDTOList);
        Assert.successResponse(stockResponse);

        /** 3、保存主订单 **/
        OrderDO orderDO = ObjectTransformer.transform(orderDTO, OrderDO.class);
        int result = orderMapper.insertOrder(orderDO);
        Assert.singleRowAffected(result);

        /** 4、保存订单明细 **/
        List<OrderItemDO> orderItemDOList = ObjectTransformer.transform(orderItemDTOList, OrderItemDO.class);
        result = orderItemMapper.insertOrderItems(orderItemDOList);
        Assert.totalRowsAffected(result, orderItemDOList);

        return true;
    }

    @Override
    public List<OrderDTO> orderList(PageQueryOrdersDTO param){
        List<OrderDO> orderDTOS = orderMapper.orderList(param);
        List<OrderDTO> list = orderDTOS.stream().map(this::convertToOrderDTO).collect(Collectors.toList());
        return list;
    }

    private OrderItemDTO convertToOrderItemDTO(OrderItemDO itemDO){
        OrderItemDTO data = OrderItemDTO.builder()
                .orderNo(itemDO.getOrderNo())
                .id(itemDO.getId())
                .amount(itemDO.getAmount())
                .number(itemDO.getNumber())
                .goodsId(itemDO.getGoodsId())
                .build();
        return data;
    }

    private OrderDTO convertToOrderDTO(OrderDO orderDO){
        List<OrderItemDO> orderItemDOS = orderItemMapper.itemList(orderDO.getOrderNo());
        List<OrderItemDTO> orderItemDTOList = orderItemDOS.stream().map(this::convertToOrderItemDTO).collect(Collectors.toList());

        OrderDTO data = OrderDTO.builder()
                .orderNo(orderDO.getOrderNo())
                .amount(orderDO.getAmount())
                .couponRecordId(orderDO.getCouponRecordId())
                .orderItemDTOList(orderItemDTOList)
                .userId(orderDO.getUserId())
                .status(orderDO.getStatus())
                .build();
        return data;
    }

    @Override
    public String order(OrderDTO orderDTO){
        //查询用户信息
        BaseResponse<UserDTO> user = userFeign.getUser(orderDTO.getUserId());

        return JSONUtil.toJSONString(user.getData());
    }
}
