package com.zxh.common.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Data
@ConfigurationProperties(prefix = "zxh")
public class ZxhConfig {
    /** 上传路径 */
    private String profile;

    @PostConstruct
    public void init() {
        System.out.println("Profile: " + profile);  // 检查 profile 是否正确注入
    }

    public String getProfileImg(){
        return profile+"images/";
    }
}



