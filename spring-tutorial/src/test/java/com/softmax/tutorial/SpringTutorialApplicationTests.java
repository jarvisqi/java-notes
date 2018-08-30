package com.softmax.tutorial;

import com.softmax.tutorial.service.LimitService;
import com.softmax.tutorial.util.JedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringTutorialApplicationTests {

    @Test
    public void limitRequest() throws InterruptedException {
        LimitService service = new LimitService();
        for (int i = 1; i <= 30; i++) {
            boolean result = service.limitApiRequest("sms.send", 10, 60);
            Thread.sleep(500);

            System.out.printf("第 %d 次请求\n", i);
            if (result) {
                System.out.println("---------------------- 接口限流");
                JedisUtil.release();
                break;
            } else {
                System.out.println("---------------------- 调用 sms.send api ");
            }

        }

    }

}
