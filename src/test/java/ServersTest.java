import org.junit.Test;
import org.openwilma.java.OpenWilma;
import org.openwilma.java.classes.WilmaServer;
import org.openwilma.java.classes.errors.Error;
import org.openwilma.java.listeners.WilmaServersListener;

import java.util.List;

/**
 * This test is for Login
 */

public class ServersTest {

    @Test
    public void loadServers() {
        System.out.println("Fetching servers");
        OpenWilma.wilmaServers(new WilmaServersListener() {
            @Override
            public void onFetchWilmaServers(List<WilmaServer> wilmaServerList) {
                for (WilmaServer server : wilmaServerList) {
                    System.out.println("server name: "+server.getName());
                    System.out.println("server url: "+server.getServerURL());
                    System.out.println("------------");
                }
            }

            @Override
            public void onFailed(Error error) {
                System.out.println("FAILED!");
                System.out.println("Error code: "+error.getErrorType());
                System.out.println(error.getMessage());
            }
        });
    }
}
