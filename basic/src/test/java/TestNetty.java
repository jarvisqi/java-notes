import com.softmax.netty.example.HttpServer;
import org.junit.Test;

/**
 * @author Jarvis
 * @date 2019/3/18
 */
public class TestNetty {

    @Test
    public void testServer() throws InterruptedException {
        HttpServer server = new HttpServer(3002);
        server.start();
    }
}
