import com.softmax.spring.config.BeanConfiguration;
import com.softmax.spring.service.AccountService;
import com.softmax.spring.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 * @author Jarvis
 * @date 2018/7/5
 */
public class TestConfigurationAnnotation {
    /**
     * 测试Spring IOC
     *
     * @throws Exception
     */
    @Test
    public void testAccountByConfigurationAnnotation() throws Exception {
        AnnotationConfigApplicationContext config = new AnnotationConfigApplicationContext(BeanConfiguration.class);

        //名称必须BeanConfiguration中工程方法名称一致
        AccountService accountService = (AccountService) config.getBean("accountService");
        accountService.doSomething();
    }

    /**
     * 测试Spring IOC
     *
     * @throws Exception
     */
    @Test
    public void testUserByConfigurationAnnotation() throws Exception {
        AnnotationConfigApplicationContext config = new AnnotationConfigApplicationContext(BeanConfiguration.class);

        //名称必须BeanConfiguration中工程方法名称一致
        UserService userService = (UserService) config.getBean("userService");
        userService.done();
    }

}
