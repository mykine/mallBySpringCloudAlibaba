package cn.mykine.mall.order.feign.goods;


import cn.mykine.mall.common.base.BaseResponse;
import cn.mykine.mall.common.dto.OrderItemDTO;
import cn.mykine.mall.order.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
    name = "mall-goods",
    fallback = GoodsFeignFallback.class,
    configuration = FeignConfig.class
)
public interface GoodsFeign {

    @PutMapping("/goods/stock")
    BaseResponse checkAndDecreaseStock(@RequestBody List<OrderItemDTO> orderItemDTOList);

}
