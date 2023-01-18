package cn.mykine.mall.coupon.service.impl;

import cn.mykine.mall.common.base.ResponseEnum;
import cn.mykine.mall.common.dto.CouponRecordDTO;
import cn.mykine.mall.common.exception.BusinessException;
import cn.mykine.mall.common.util.Assert;
import cn.mykine.mall.common.util.ObjectTransformer;
import cn.mykine.mall.coupon.mapper.CouponMapper;
import cn.mykine.mall.coupon.mapper.CouponRecordMapper;
import cn.mykine.mall.coupon.model.CouponDO;
import cn.mykine.mall.coupon.model.CouponRecordDO;
import cn.mykine.mall.coupon.service.ICouponRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CouponRecordServiceImpl implements ICouponRecordService {

    @Resource
    CouponMapper couponMapper;

    @Resource
    CouponRecordMapper couponRecordMapper;

    @Override
    public boolean receiveCoupon(CouponRecordDTO couponRecordDTO) {
        long couponId = couponRecordDTO.getCouponId();
        CouponDO couponDO = couponMapper.selectCouponById(couponId);
        Assert.notNull(couponDO);

        /** 判断是否已经超过发券数量 **/
        long newTakeCount = couponDO.getTakeCount() + 1;
        if (newTakeCount > couponDO.getQuota()) {
            throw new BusinessException(ResponseEnum.COUPON_NOT_ENOUGH);
        }

        /** 新增优惠券领取记录 **/
        couponRecordDTO.setStatus(0);
        CouponRecordDO couponRecordDO = ObjectTransformer.transform(couponRecordDTO, CouponRecordDO.class);
        int result = couponRecordMapper.insertCouponRecord(couponRecordDO);
        Assert.singleRowAffected(result);

        /** 更新优惠券已领取数量 **/
        couponDO.setTakeCount(newTakeCount);
        result = couponMapper.updateCouponTakeCount(couponDO);
        return Assert.singleRowAffected(result);
    }

    @Override
    public boolean useCoupon(CouponRecordDTO couponRecordDTO) {

        /** 根据用户ID和优惠券ID获取优惠券记录，防止越权，并且校验优惠券状态 **/
        CouponRecordDO couponRecordDO = ObjectTransformer.transform(couponRecordDTO, CouponRecordDO.class);
        couponRecordDO = couponRecordMapper.selectCouponRecord(couponRecordDO);
        checkCouponRecordStatus(couponRecordDO);

        couponRecordDO.setStatus(1);
        int result = couponRecordMapper.updateCouponRecordStatus(couponRecordDO);
        boolean success = Assert.singleRowAffected(result);

        //TODO 根据CouponID异步更新优惠券已使用数量

        return success;
    }

    /**
     * 校验优惠券状态
     *
     * @param couponRecordDO
     */
    private void checkCouponRecordStatus(CouponRecordDO couponRecordDO) {
        Assert.notNull(couponRecordDO);
        if(couponRecordDO.getStatus() == 1) {
            throw new BusinessException(ResponseEnum.COUPON_USED);
        } else if (couponRecordDO.getStatus() == -1) {
            throw new BusinessException(ResponseEnum.COUPON_EXPIRED);
        }
    }
}
