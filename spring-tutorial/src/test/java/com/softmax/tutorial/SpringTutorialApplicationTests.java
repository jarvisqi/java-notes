package com.softmax.tutorial;

import com.softmax.tutorial.service.LimitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringTutorialApplicationTests {

    @Test
    public void limitRequest() {
        LimitService service = new LimitService();
        boolean result = service.limitApiRequest("sms.send", 10, 30);
        if (result) {
            System.out.println("接口限流");
        }
    }

}
