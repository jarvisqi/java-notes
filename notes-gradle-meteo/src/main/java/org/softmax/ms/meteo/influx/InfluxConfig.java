package org.softmax.ms.meteo.influx;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class InfluxConfig {
    private static final Logger logger = LoggerFactory.getLogger(InfluxConfig.class);

    @Value("${spring.influx.url}")
    private String influxDBUrl;

    @Value("${spring.influx.user}")
    private String userName;

    @Value("${spring.influx.password}")
    private String password;

    @Value("${spring.influx.database}")
    private String database;

    /**
     * 异步插入：
     * enableBatch这里第一个是point的个数，第二个是时间，单位毫秒
     * point的个数和时间是联合使用的，如果满100条或者60 * 1000毫秒
     * 满足任何一个条件就会发送一次写的请求。
     */
    @Bean(name = "influxDB")
    public InfluxDB influxdb() {
        InfluxDB influxDB = InfluxDBFactory.connect(influxDBUrl, userName, password);
        try {
            influxDB.setDatabase(database).enableBatch(100, 1000 * 60, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            logger.error("fail enableBatch:{0}", e);
        } finally {
            //设置默认策略
            influxDB.setRetentionPolicy("autogen");
        }
        //设置日志输出级别
        influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
        return influxDB;
    }
}