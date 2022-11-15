import org.junit.jupiter.api.Test;
import org.softmax.gradle.grb.Grib2Util;

import java.io.IOException;

public class Grib2Test {

    @Test
    public void test() throws IOException {

        Grib2Util util = new Grib2Util();
        util.readGrid();
    }
}
