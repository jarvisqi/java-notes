package com.springmarvel;

import com.marvel.MarvelApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Jarvis
 * @date 2018/10/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarvelApplication.class)
public class MarvelApplicationTest {

    @Test
    public void memoryTest() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        System.out.println(maxMemory/1024/1024/1024);
        System.out.println(freeMemory/1024/1024/1024);
        System.out.println(totalMemory/1024/1024/1024);
    }

}
