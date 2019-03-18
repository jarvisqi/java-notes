import com.netty.example.HttpServer;
import org.junit.jupiter.api.Test;

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
