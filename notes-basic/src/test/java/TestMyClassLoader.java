import com.softmax.basic.jvm.sample.MyClassLoader;
import org.junit.Test;

/**
 * @author Jarvis
 * @date 2018/8/20
 */
public class TestMyClassLoader {


    @Test
    public void testClassLoader() throws Exception {
        MyClassLoader loader = new MyClassLoader("D:\\Learning\\java-learning\\java-samples\\base-code\\target\\classes\\com\\basic\\jvmcode\\Person.class");
        Class<?> cls = Class.forName("com.softmax.basic.jvm.sample.Person", true, loader);
        Object object = cls.getDeclaredConstructor().newInstance();
        System.out.println(object);
        System.out.println(object.getClass().getClassLoader());
    }


}
