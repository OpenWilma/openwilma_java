import com.google.gson.Gson;
import org.junit.Test;
import org.openwilma.java.OpenWilma;
import org.openwilma.java.classes.Authentication;
import org.openwilma.java.classes.Role;
import org.openwilma.java.classes.WilmaServer;
import org.openwilma.java.classes.WilmaUserTypes;
import org.openwilma.java.classes.errors.Error;
import org.openwilma.java.classes.errors.ExceptionError;
import org.openwilma.java.listeners.WilmaLoginListener;
import org.openwilma.java.listeners.WilmaRolesListener;

import java.util.List;

/**
 * This test is for Login
 */

public class RolesTest {

    @Test
    public void testRoles() {
        OpenWilma.login(new WilmaServer("url"), "username", "password", new WilmaLoginListener() {
            @Override
            public void onLogin(Authentication wilmaSession) {
                System.out.println("Login successful");
                System.out.println("SessionId: "+wilmaSession.getSessionId());
                System.out.println("Fetching roles");
                OpenWilma.roles(wilmaSession, new WilmaRolesListener() {
                    @Override
                    public void onFetchRoles(List<Role> roles) {
                        System.out.println("Fetched roles!");
                        for (Role role : roles) {
                            System.out.println(new Gson().toJson(role));
                            System.out.println("----------");

                        }
                    }

                    @Override
                    public void onFailed(Error error) {
                        System.out.println("FAILED!");
                        System.out.println("Error code: "+error.getErrorType());
                        System.out.println(error.getMessage());
                        if (error instanceof ExceptionError) {
                            ExceptionError exceptionError = (ExceptionError) error;
                            exceptionError.getException().printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onFailed(Error error) {
                System.out.println("FAILED!");
                System.out.println("Error code: "+error.getErrorType());
                System.out.println(error.getMessage());
                if (error instanceof ExceptionError) {
                    ExceptionError exceptionError = (ExceptionError) error;
                    exceptionError.getException().printStackTrace();
                }
            }
        });
    }
}
