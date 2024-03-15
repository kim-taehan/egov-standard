package com.skcc.egovcore.core.config.aop;

import com.skcc.egovcore.core.config.aop.aspect.CheckMethodAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AopConfig {
    @Bean
    public CheckMethodAspect checkMethodAspect() {
        return new CheckMethodAspect();
    }


}
