package com.softmax.marvel;

import com.softmax.marvel.event.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MarvelApplication.class)
public class EventTest {

    @Autowired
    private UserService userService;

    @Test
    public void userRegisterEvent() {

        userService.register("朱元璋");
    }

}
