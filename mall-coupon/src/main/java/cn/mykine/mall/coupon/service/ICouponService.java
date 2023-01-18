package cn.mykine.mall.coupon.service;


import cn.mykine.mall.common.dto.CouponDTO;
import cn.mykine.mall.common.dto.PageQueryParam;

import java.util.List;

/**
 * 优惠券Service
 */
public interface ICouponService {

    /**
     * 创建优惠券
     *
     * @param couponDTO
     * @return
     */
    boolean createCoupon(CouponDTO couponDTO);


    /**
     * 查询优惠券
     *
     */
    CouponDTO queryCoupon(Long id);

    /**
     * 分页查询优惠券列表
     *
     */
    List<CouponDTO> pageQueryCoupon(PageQueryParam pageQueryParam);

}
