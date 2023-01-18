package cn.mykine.mall.order.feign.goods;

import cn.mykine.mall.common.base.BaseResponse;
import cn.mykine.mall.common.base.ResponseEnum;
import cn.mykine.mall.common.dto.OrderItemDTO;
import cn.mykine.mall.common.exception.BusinessException;

import java.util.List;

public class GoodsFeignFallback implements GoodsFeign {

    @Override
    public BaseResponse checkAndDecreaseStock(List<OrderItemDTO> orderItemDTOList) {
        throw new BusinessException(ResponseEnum.FEIGN_CALL_EXCEPTION);
    }
}
