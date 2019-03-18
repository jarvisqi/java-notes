import com.basic.jvm.example.MyClassLoader;
import org.junit.jupiter.api.Test;

/**
 * @author Jarvis
 * @date 2018/8/20
 */
public class TestMyClassLoader {


    @Test
    public void testClassLoader() throws Exception {
        MyClassLoader loader = new MyClassLoader("D:\\Learning\\java-learning\\java-tutorial\\base-code\\target\\classes\\com\\basic\\jvmcode\\Person.class");
        Class<?> cls = Class.forName("com.basic.jvm.example.Person", true, loader);
        Object object = cls.getDeclaredConstructor().newInstance();
        System.out.println(object);
        System.out.println(object.getClass().getClassLoader());
    }


}
