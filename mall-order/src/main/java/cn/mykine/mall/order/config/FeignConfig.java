package cn.mykine.mall.order.config;


import cn.mykine.mall.order.feign.coupon.CouponFeignFallback;
import cn.mykine.mall.order.feign.goods.GoodsFeignFallback;
import org.springframework.context.annotation.Bean;

public class FeignConfig {

    @Bean
    public GoodsFeignFallback goodsFeignFallback() {
        return new GoodsFeignFallback();
    }

    @Bean
    public CouponFeignFallback couponFeignFallback() {
        return new CouponFeignFallback();
    }

}
