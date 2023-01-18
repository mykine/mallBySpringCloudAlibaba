package cn.mykine.mall.order.feign.coupon;

import cn.mykine.mall.common.base.BaseResponse;
import cn.mykine.mall.common.base.ResponseEnum;
import cn.mykine.mall.common.dto.CouponRecordDTO;
import cn.mykine.mall.common.exception.BusinessException;

public class CouponFeignFallback implements CouponFeign {

    @Override
    public BaseResponse receiveCoupon(CouponRecordDTO couponRecordDTO) {
        throw new BusinessException(ResponseEnum.FEIGN_CALL_EXCEPTION);
    }

    @Override
    public BaseResponse useCoupon(CouponRecordDTO couponRecordDTO) {
        throw new BusinessException(ResponseEnum.FEIGN_CALL_EXCEPTION);
    }
}
