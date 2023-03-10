package cn.mykine.mall.goods.service.impl;


import cn.mykine.mall.common.base.Constants;
import cn.mykine.mall.common.base.ResponseEnum;
import cn.mykine.mall.common.dto.GoodsDTO;
import cn.mykine.mall.common.dto.OrderItemDTO;
import cn.mykine.mall.common.dto.PageQueryGoodsDTO;
import cn.mykine.mall.common.exception.BusinessException;
import cn.mykine.mall.common.util.Assert;
import cn.mykine.mall.common.util.JSONUtil;
import cn.mykine.mall.common.util.ObjectTransformer;
import cn.mykine.mall.goods.aspect.MyCacheable;
import cn.mykine.mall.goods.mapper.GoodsMapper;
import cn.mykine.mall.goods.model.GoodsDO;
import cn.mykine.mall.goods.service.IGoodsService;
import cn.mykine.mall.goods.utils.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class GoodsServiceImpl implements IGoodsService {

    private static final Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);

    @Resource
    GoodsMapper goodsMapper;

    @Resource
    RedisUtil redisUtil;

    @Override
    public boolean addGoods(GoodsDTO goodsDTO) {
        GoodsDO goodsDO = ObjectTransformer.transform(goodsDTO, GoodsDO.class);
        int result = goodsMapper.insertGoods(goodsDO);
        return Assert.singleRowAffected(result);
    }

    @Override
    public List<GoodsDTO> pageQueryGoods(PageQueryGoodsDTO param){
        List<GoodsDO> goodsDOS = goodsMapper.pageQueryGoods(param);
        List<GoodsDTO> list = goodsDOS.stream().map(this::convertToGoodsDTO).collect(Collectors.toList());
        return list;
    }

    private GoodsDTO convertToGoodsDTO(GoodsDO goodsDO){
        GoodsDTO data = GoodsDTO.builder()
                .id(goodsDO.getId())
                .description(goodsDO.getDescription())
                .name(goodsDO.getName())
                .price(goodsDO.getPrice())
                .stock(goodsDO.getStock())
                .type(goodsDO.getType())
                .build();
        return data;
    }

//    @Override
//    public GoodsDTO getGoods(Long id) {
//        GoodsDO goodsDO = null;
//        try {
//
//            String cacheKey = String.format(Constants.GOODS_CACHE_KEY, id);
//            goodsDO = (GoodsDO)redisUtil.get(cacheKey);
//            logger.info("key:{}, value:{}", cacheKey, goodsDO);
//
//            if(null==goodsDO) {
//                goodsDO = goodsMapper.selectGoodsById(id);
//                Assert.notNull(goodsDO);
//                redisUtil.set(cacheKey,goodsDO,300);
//            }
//        }catch (Exception e){
//            logger.warn("GoodsServiceImpl.getGoods err,id={}",id,e);
//        }
//        return ObjectTransformer.transform(goodsDO, GoodsDTO.class);
//    }


    @MyCacheable(cacheName = "mall:goods:",key = "#id",expireInSeconds = 3,waitInSeconds = 2)
    @Override
    public GoodsDTO getGoods(Long id) {
        GoodsDO goodsDO = goodsMapper.selectGoodsById(id);
        Assert.notNull(goodsDO);
        return ObjectTransformer.transform(goodsDO, GoodsDTO.class);
    }

    @Override
    public GoodsDTO getGoodsNameAndPrice(Long id) {
        GoodsDO goodsDO = null;
        try (Jedis jedis = new Jedis("localhost", 6379);) {

            String cacheKey = String.format(Constants.GOODS_CACHE_KEY, id);
            Map<String, String> cacheValue = jedis.hgetAll(cacheKey);
            logger.info("key:{}, value:{}", cacheKey, cacheValue);

            if(CollectionUtils.isEmpty(cacheValue)) {
                goodsDO = goodsMapper.selectGoodsById(id);
                Assert.notNull(goodsDO);

                cacheValue = new HashMap<>();
                cacheValue.put("name", goodsDO.getName());
                cacheValue.put("price", goodsDO.getPrice().toPlainString());
                jedis.hmset(cacheKey, cacheValue);
            } else {
                goodsDO = new GoodsDO();
                goodsDO.setName(cacheValue.get("name"));
                goodsDO.setPrice(new BigDecimal(cacheValue.get("price")));
            }
        }
        return ObjectTransformer.transform(goodsDO, GoodsDTO.class);
    }

    /**
     * ??????????????????
     *
     * <p>
     * update t_goods
     *    set stock = stock - #{number}
     *  where id = #{goodsId}
     *    and stock >= #{number};
     * </p>
     *
     * ?????????stock >= #{number} <br/>
     * ?????????????????????????????????????????????????????????????????????????????????????????????????????????
     *
     * @param orderItemDTOList
     * @return
     */
    @Override
    public boolean decreaseStock(List<OrderItemDTO> orderItemDTOList) {
        GoodsDO goodsDO;
        List<GoodsDO> goodsDOList = new ArrayList<>();
        for (OrderItemDTO orderItem : orderItemDTOList) {
            goodsDO = new GoodsDO();
            goodsDO.setId(orderItem.getGoodsId());
            // ????????????stock??????????????????????????????
            goodsDO.setStock(orderItem.getNumber());
            goodsDOList.add(goodsDO);
        }
        int result = goodsMapper.updateGoodsListStock(goodsDOList);
        if (result != goodsDOList.size()) {
            // ????????????????????????????????????????????????????????????????????????????????????
            throw new BusinessException(ResponseEnum.GOODS_STOCK_NOT_ENOUGH);
        }
        return true;
    }

}
