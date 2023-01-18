package cn.mykine.mall.order.controller;

import cn.mykine.mall.common.base.BaseResponse;
import cn.mykine.mall.common.base.ResponseEnum;
import cn.mykine.mall.common.dto.OrderDTO;
import cn.mykine.mall.common.dto.PageQueryOrdersDTO;
import cn.mykine.mall.order.service.IOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单服务Controller
 */
@RestController
public class OrderController {

    @Resource
    private IOrderService orderService;

    /**
     * 创建订单接口
     *
     * @param orderDTO
     * @return
     */
    @PostMapping("/order")
    BaseResponse createOrder(@RequestBody OrderDTO orderDTO) {
        //TODO 对 DTO 进行前置校验
        orderService.createOrder(orderDTO);
        return BaseResponse.success();
    }

//    /**
//     * 获取订单
//     *
//     * @return
//     */
//    @GetMapping("/order/{orderNo}")
//    BaseResponse<OrderDTO> getOrder(@PathVariable(value = "orderNo") Long orderNo){
//        try (Entry entry = SphU.entry("mall/order")) {
//            OrderDTO orderDTO = new OrderDTO();
//            orderDTO.setOrderNo(orderNo);
//            return BaseResponse.success(orderDTO);
//        } catch (BlockException e) {
//            return new BaseResponse<>(ResponseEnum.SYSTEM_BUSY);
//        }
//    }

    @GetMapping("/order2/{orderNo}")
    BaseResponse<OrderDTO> getOrder2(@PathVariable(value = "orderNo") Long orderNo){
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderNo(orderNo);
            return BaseResponse.success(orderDTO);
    }

    @PostMapping("/order/list")
    BaseResponse<List<OrderDTO>> list(@RequestBody PageQueryOrdersDTO param){
        List<OrderDTO> list = orderService.orderList(param);
        return BaseResponse.success(list);
    }

    public BaseResponse fallback(Long orderNo, Throwable e) {
        return new BaseResponse<>(ResponseEnum.SYSTEM_BUSY);
    }

}
