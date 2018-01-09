package org.apache.skywalking.testcase.httpasyncclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@PropertySource("classpath:application.properties")
public class MysqlConfig {

    @Value(value = "${mysql.host}")
    private String host;
    @Value(value = "${mysql.username}")
    private String userName;
    @Value(value = "${mysql.password}")
    private String password;

    public  String getUrl() {
        String url = "jdbc:mysql://" + host + "/sky?useUnicode=true&characterEncoding=UTF-8";
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
