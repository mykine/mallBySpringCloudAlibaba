package cn.mykine.mall.goods.service.impl;


import cn.mykine.mall.common.base.Constants;
import cn.mykine.mall.common.base.ResponseEnum;
import cn.mykine.mall.common.dto.GoodsDTO;
import cn.mykine.mall.common.dto.OrderItemDTO;
import cn.mykine.mall.common.dto.PageQueryGoodsDTO;
import cn.mykine.mall.common.exception.BusinessException;
import cn.mykine.mall.common.util.Assert;
import cn.mykine.mall.common.util.ObjectTransformer;
import cn.mykine.mall.goods.aspect.MyCacheable;
import cn.mykine.mall.goods.mapper.GoodsMapper;
import cn.mykine.mall.goods.model.GoodsDO;
import cn.mykine.mall.goods.service.IGoodsService;
import cn.mykine.mall.goods.service.ITestService;
import cn.mykine.mall.goods.utils.RedisUtil;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TestServiceImpl implements ITestService {

    private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    @Resource
    GoodsMapper goodsMapper;

    @Resource
    RedisUtil redisUtil;


    /**
     * 布隆过滤器，利用二进制数组结构存储标记数据，用于检测一个元素是不是集合中的一员，
     *    如果检测结果为是，该元素可能在集合中
     *    如果检测结果为否，该元素一定不在集合中
     * 使用情景：预防缓存击穿
     * 内部数据结构：二进制数组
     * 算法思想：将元素值使用k个哈希函数进行哈希运算，得到k个值，
     *         对应到长度为m的数组中的k个下标，
     *         存储时，将这k个下标数组值标记为1；
     *         查询时，将这k个下标数组值取出来，看是否是标记了1，
     *         如果都是1，表示该目标元素可能在集合中，
     *         如果存在0，表示该元素一定不在集合中。
     *
     * 缺点：不能随意进行元素删除操作，否则可能影响共用数组下标的其他元素
     * 操作流程：
     *      1.确定布隆过滤器数组大小、误差率
     *      2.初始化布隆过滤器，将符合条件的元素放入布隆过滤器中，形成元素集合
     *      3.检测目标元素是否在集合中
     * */
    @Override
    public String testBloomFilter(Long id) {
        boolean res = bloomFilterByGuava(id);
        String result = id + (res ? "可能是集合元素" : "不是集合元素");
        return result;
    }

    /**
     * 利用Guava工具包实现布隆过滤器,检测元素是否在集合中
     * */
    private boolean bloomFilterByGuava(Long id){
        //数组大小
        int size = 1000;
        //期望的误差率
        double fpp = 0.01;
        //创建布隆过滤器，指定存储的数据类型
        BloomFilter<Long> bloomFilter =
                BloomFilter.create(Funnels.longFunnel(),size,fpp);
        //初始化布隆过滤器
        for (long i = 0; i < size; i++) {
            bloomFilter.put(i);
        }
        //自测一波误差率
        int count = 0;
        for (long j = size; j < size*2; j++) {
            if(bloomFilter.mightContain(j)){
                count++;
                System.out.println(j+"误判了~");
            }
        }
        System.out.println("误判个数："+count);

        //检测目标元素
        if(bloomFilter.mightContain(id)){
            return true;
        }
        return false;
    }


}
