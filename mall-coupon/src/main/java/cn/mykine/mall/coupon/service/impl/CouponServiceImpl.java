package cn.mykine.mall.coupon.service.impl;


import cn.mykine.mall.common.dto.CouponDTO;
import cn.mykine.mall.common.util.Assert;
import cn.mykine.mall.common.util.ObjectTransformer;
import cn.mykine.mall.coupon.mapper.CouponMapper;
import cn.mykine.mall.coupon.model.CouponDO;
import cn.mykine.mall.common.dto.PageQueryParam;
import cn.mykine.mall.coupon.service.ICouponService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl implements ICouponService {

    @Resource
    CouponMapper couponMapper;

    @Override
    public boolean createCoupon(CouponDTO couponDTO) {
        CouponDO couponDO = ObjectTransformer.transform(couponDTO, CouponDO.class);
        int result = couponMapper.insertCoupon(couponDO);
        return Assert.singleRowAffected(result);
    }

    public CouponDTO queryCoupon(Long id){
        CouponDO couponDO = couponMapper.selectCouponById(id);
        CouponDTO result = CouponDTO.builder()
                .id(couponDO.getId())
                .title(couponDO.getTitle())
                .quota(couponDO.getQuota())
                .status(couponDO.getStatus())
                .takeCount(couponDO.getTakeCount())
                .withAmount(couponDO.getWithAmount())
                .usedAmount(couponDO.getUsedAmount())
                .usedCount(couponDO.getUsedCount())
                .build();
        return result;
    }

    public List<CouponDTO> pageQueryCoupon(PageQueryParam pageQueryParam){
        List<CouponDO> couponDOS = couponMapper.selectCouponListByPage(pageQueryParam);
        List<CouponDTO> list = couponDOS.stream().map(this::convertCouponDTO).collect(Collectors.toList());
        return list;
    }

    private CouponDTO convertCouponDTO(CouponDO couponDO){
        CouponDTO data = CouponDTO.builder()
                .id(couponDO.getId())
                .usedCount(couponDO.getUsedCount())
                .title(couponDO.getTitle())
                .usedAmount(couponDO.getUsedAmount())
                .withAmount(couponDO.getWithAmount())
                .takeCount(couponDO.getTakeCount())
                .status(couponDO.getStatus())
                .quota(couponDO.getQuota())
                .build();
        return data;
    }

}
