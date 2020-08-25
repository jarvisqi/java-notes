import com.softmax.spring.service.AccountService;
import com.softmax.spring.service.impl.AccountServiceImpl;
import com.test.impl.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;

@RunWith(Parameterized.class)
public class TestTransactional {

    @Parameterized.Parameter(0)
    public AccountService accountService;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> params = new ArrayList<>();
        params.add(new Object[]{new AccountServiceImpl()});
        return params;
    }


    @Test
    public void testSave() {

        accountService.saveSomething();

    }

}
