package com.yueyitong.emqxdemo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Emqx服务端配置
 *
 * @author yangxin
 * @date 2020/5/3
 */
@Data
@ConfigurationProperties(prefix = "emqx.server")
@Component
public class EmqxConfig {

    private String broker;

    private String clientId;

    private String username;

    private String password;

    private Boolean cleanSession;

    private String websocketUrl;

}