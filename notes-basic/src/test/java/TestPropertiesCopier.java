import com.test.Account;
import com.test.PropertiesCopier;
import com.test.impl.*;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class TestPropertiesCopier {
    @Parameterized.Parameter(0)
    public PropertiesCopier propertiesCopier;

    private static final List<Integer> testTimes = Arrays.asList(100, 1000, 10_000, 1_00_000, 1_000_000);

    // ���Խ���� markdown ������ʽ���
    private static final StringBuilder resultBuilder = new StringBuilder("|ʵ��|100|1,000|10,000|100,000|1,000,000|\n")
            .append("|----|----|----|----|----|----|\n");

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> params = new ArrayList<>();
        params.add(new Object[]{new StaticCglibBeanCopierProperties()});
        params.add(new Object[]{new CglibBeanCopierProperties()});
        params.add(new Object[]{new SpringBeanUtilsPropertiesCopier()});
        params.add(new Object[]{new CommonsBeanUtilsPropertiesCopier()});
        params.add(new Object[]{new CommonsPropertyUtilsPropertiesCopier()});

        return params;
    }

    @Before
    public void setUp() {
        String name = propertiesCopier.getClass().getSimpleName().replace("PropertiesCopier", "");
        resultBuilder.append("|").append(name).append("|");
    }

    @Test
    public void copyProperties() throws Exception {
        Account source = new Account(1, "test1", 30D);
        Account target = new Account();

        // Ԥ��һ��
        propertiesCopier.copyProperties(source, target);
        for (Integer testTime : testTimes) {
            long start = System.nanoTime();
            for (int i = 0; i < testTime; i++) {
                propertiesCopier.copyProperties(source, target);
            }
            resultBuilder.append((System.nanoTime() - start) / 1_000_000D).append("|");
        }
        resultBuilder.append("\n");
    }

    @AfterClass
    public static void tearDown() {
        System.out.println("���Խ��");
        System.out.println(resultBuilder);
    }
}
