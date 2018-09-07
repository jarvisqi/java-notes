import com.basiccode.jvmcode.MyClassLoader;
import org.junit.jupiter.api.Test;

/**
 * @author Jarvis
 * @date 2018/8/20
 */
public class TestMyClassLoader {


    @Test
    public void testClassLoader() throws Exception {
        MyClassLoader loader = new MyClassLoader("D:\\Learning\\java-learning\\java-tutorial\\base-code\\target\\classes\\com\\basiccode\\jvmcode\\Person.class");
        Class<?> cls = Class.forName("com.basiccode.jvmcode.Person", true, loader);
        Object object = cls.getDeclaredConstructor().newInstance();
        System.out.println(object);
        System.out.println(object.getClass().getClassLoader());
    }


}
