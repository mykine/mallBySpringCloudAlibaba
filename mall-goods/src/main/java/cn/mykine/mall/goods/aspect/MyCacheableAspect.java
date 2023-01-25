package cn.mykine.mall.goods.aspect;

import cn.mykine.mall.common.base.ResponseEnum;
import cn.mykine.mall.common.exception.BusinessException;
import cn.mykine.mall.goods.utils.RedisUtil;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 自定义缓存切面类
 * */
@Slf4j
@Component
@Aspect
@ConfigurationProperties(prefix = "mycacheable.rate.limit")
public class MyCacheableAspect {

    @Autowired
    private RedisUtil redisUtil;

    public void setMap(Map<String, Double> map) {
        this.map = map;
    }

    /**
     * key - 需要限流的方法
     * value - 限流速率
     */
    private Map<String, Double> map;

    private Map<String, RateLimiter> rateLimiterMap = Maps.newHashMap();

    /**
     * 切面类创建完成后立即执行的初始化方法
     * */
    @PostConstruct
    public void initRateLimiterMap(){
            if( !CollectionUtils.isEmpty(map) ){
                map.forEach((method,permits)->{
                    RateLimiter rateLimiter = RateLimiter.create(permits);
                    rateLimiterMap.put(method,rateLimiter);
                });
            }
    }

    /**
     * 定义切入点,以标记了MyCacheable注解的方法作为目标对象
     * */
    @Pointcut("@annotation(cn.mykine.mall.goods.aspect.MyCacheable)")
    public void pointCut(){

    }

//    /**
//     * 对pointCut方法对应的切入点进行代码增强，参数是连接点(即业务代码方法的调用执行)
//     * */
//    @Around("pointCut()")
//    public Object doAroundCache(ProceedingJoinPoint joinPoint){
//
//    }

    /**
     * 另一种简洁写法定义连接点和增强代码
     * 对pointCut方法对应的切入点进行代码增强，参数是连接点(即业务代码方法的调用执行)
     * */
    @Around("@annotation(myCacheable)")
    public Object doAroundCache(ProceedingJoinPoint joinPoint,MyCacheable myCacheable) throws Throwable {
        /**
         * 通过连接点对象获取执行的业务方法签名信息,
         * 因为MyCacheable注解定义只作用在方法上，所以可以直接转换为方法签名类型
         * */
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        //方法参数名数组
        String[] parameterNames = discoverer.getParameterNames(signature.getMethod());
        //方法参数值数组,与参数名数组索引一一对应
        Object[] args = joinPoint.getArgs();

        //通过el表达式解析出key值
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(myCacheable.key());
        EvaluationContext context = new StandardEvaluationContext();
        for ( int i=0; i<parameterNames.length; i++ ) {
            context.setVariable(parameterNames[i],args[i]);
        }

        //获取缓存key，先查询缓存
        String key = myCacheable.cacheName()+expression.getValue(context).toString();
        Object res = redisUtil.get(key);
        if(null!=res){
            log.info("redis has data,key={},res={}",key,res);
            return res;
        }

        //单机限流防止缓存雪崩
        if(null != rateLimiterMap.get(signature.getMethod().getName())){
            RateLimiter rateLimiter = rateLimiterMap.get(signature.getMethod().getName());
            if( rateLimiter != null ){
                if(myCacheable.waitInSeconds()<=0){
                    //没设置获取令牌桶超时时间时，使用acquire()方法,否则使用tryAcquire()
                    rateLimiter.acquire();
                }else {
                    boolean resTry = rateLimiter.tryAcquire(myCacheable.waitInSeconds(), TimeUnit.SECONDS);
                    if(!resTry){
                        throw new BusinessException(ResponseEnum.SYSTEM_BUSY);
                    }
                }
            }
        }

        //缓存为空就执行业务DB方法,写缓存并返回
        Object value = joinPoint.proceed();
        if( myCacheable.expireInSeconds() <=0 ){
            redisUtil.set(key,value);
        }else {
            redisUtil.set(key,value,myCacheable.expireInSeconds());
        }
        return value;
    }

}
