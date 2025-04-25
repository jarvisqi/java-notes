import com.softmax.mvcframe.service.AccountService;
import com.softmax.mvcframe.service.impl.AccountServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

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
