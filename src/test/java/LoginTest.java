import org.junit.Test;
import org.openwilma.java.OpenWilma;
import org.openwilma.java.classes.WilmaServer;
import org.openwilma.java.classes.errors.Error;
import org.openwilma.java.listeners.WilmaLoginListener;

/**
 * This test is for Login
 */

public class LoginTest {

    @Test
    public void testLogin() {
        OpenWilma.login(new WilmaServer("https://test.inschool.fi"), "test", "test", new WilmaLoginListener() {
            @Override
            public void onLogin(String wilmaSession) {
                System.out.println("Login successful");
                System.out.println("SessionId: "+wilmaSession);
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
