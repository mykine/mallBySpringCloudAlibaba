package cn.mykine.mall.goods.config;

import com.alibaba.cloud.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ProjectNameConfig implements EnvironmentAware {

    @Value("${spring.application.name}")
    private  String applicationName;

    @Override
    public void setEnvironment(Environment environment) {
        if(StringUtils.isBlank(System.getProperty("project.name"))){
            System.setProperty("project.name",applicationName);
        }
    }
}
