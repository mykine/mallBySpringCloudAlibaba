package cn.mykine.mall.coupon.controller;

import cn.mykine.mall.common.base.BaseResponse;
import cn.mykine.mall.common.dto.CouponDTO;
import cn.mykine.mall.common.dto.CouponRecordDTO;
import cn.mykine.mall.common.dto.PageQueryParam;
import cn.mykine.mall.coupon.service.ICouponRecordService;
import cn.mykine.mall.coupon.service.ICouponService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class
CouponController {

    @Resource
    ICouponService couponService;

    @Resource
    ICouponRecordService couponRecordService;

    /**
     * 查询优惠券
     *
     * @param
     * @return
     */
    @GetMapping("/coupon/{id}")
    BaseResponse<CouponDTO> queryCoupon(@PathVariable Long id){
        CouponDTO couponDTO = couponService.queryCoupon(id);
        return BaseResponse.success(couponDTO);
    }

    /**
     * 查询优惠券
     *
     * @param
     * @return
     */
    @GetMapping("/coupon/list/{pageNo}")
    BaseResponse<List<CouponDTO>> queryCoupon(@PathVariable Integer pageNo){
        PageQueryParam pageQueryParam = PageQueryParam.builder()
                .pageNo(pageNo)
                .pageSize(10)
                .build();

        List<CouponDTO> couponDTOS = couponService.pageQueryCoupon(pageQueryParam);
        return BaseResponse.success(couponDTOS);
    }

    /**
     * 创建优惠券
     *
     * @param couponDTO
     * @return
     */
    @PostMapping("/coupon")
    BaseResponse createCoupon(@RequestBody CouponDTO couponDTO){
        couponService.createCoupon(couponDTO);
        return BaseResponse.success();
    }

    /**
     * 领取优惠券
     *
     * @param couponRecordDTO
     * @return
     */
    @PostMapping("/couponRecord")
    BaseResponse receiveCoupon(@RequestBody CouponRecordDTO couponRecordDTO){
        couponRecordService.receiveCoupon(couponRecordDTO);
        return BaseResponse.success();
    }

    /**
     * 更新优惠券状态
     *
     * @param couponRecordDTO
     * @return
     */
    @PutMapping("/couponRecord")
    BaseResponse useCoupon(@RequestBody CouponRecordDTO couponRecordDTO){
        couponRecordService.useCoupon(couponRecordDTO);
        return BaseResponse.success();
    }

}
