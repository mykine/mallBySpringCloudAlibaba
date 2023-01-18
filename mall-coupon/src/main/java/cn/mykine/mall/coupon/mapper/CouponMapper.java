package cn.mykine.mall.coupon.mapper;

import cn.mykine.mall.coupon.model.CouponDO;
import cn.mykine.mall.common.dto.PageQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 优惠券Mapper
 */
@Mapper
public interface CouponMapper {

    /**
     * 新增优惠券
     *
     * @param couponDO
     * @return
     */
    int insertCoupon(CouponDO couponDO);

    /**
     * 查找优惠券
     *
     * @param id
     * @return
     */
    CouponDO selectCouponById(Long id);

    /**
     * 分页查询优惠券列表
     *
     * @param pageQueryParam
     * @return
     */
    List<CouponDO> selectCouponListByPage(PageQueryParam pageQueryParam);

    /**
     * 更新优惠券已领取数量
     *
     * @param couponDO
     * @return
     */
    int updateCouponTakeCount(CouponDO couponDO);

    /**
     * 更新优惠券已使用数量
     * @param couponDO
     * @return
     */
    int updateCouponUsedCount(CouponDO couponDO);
}
