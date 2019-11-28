package com.softmax.mq.courier.kafka;

import com.alibaba.fastjson.JSON;
import com.softmax.mq.courier.kafka.beans.BizData;
import com.softmax.mq.courier.kafka.beans.BizOperationLog;
import com.softmax.mq.courier.kafka.provider.KafkaSender;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author Jarvis
 * @date 2019/3/19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaTests {

    @Autowired
    private KafkaSender<BizOperationLog> kafkaSender;

    @Test
    public void kafkaSend() throws InterruptedException {

        BizOperationLog log = new BizOperationLog();
        log.setAppId("jwell-oms");
        log.setAppName("运营系统");
        log.setOptTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        log.setOptUser("建材总监-王文国");
        //发消息
        for (int i = 0; i < 20; i++) {
            String id = "RO" + Long.toString(RandomUtils.nextLong(10000000, 90000000));
            float price = RandomUtils.nextFloat(1000, 70000);
            BizData data = new BizData(id, price, "钢卷");
            log.setBeforeModifyData(JSON.toJSONString(data));

            //改价格
            price = RandomUtils.nextFloat(1000, 7000);
            //改品名
            data = new BizData(id, price, "矿石");
            log.setAfterModifyData(JSON.toJSONString(data));

            kafkaSender.send(log);
            Thread.sleep(500);
        }
    }


}
