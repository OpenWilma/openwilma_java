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
        OpenWilma.login(new WilmaServer("https://test.inschool.fi"), "mobile.testaccount@visma.local", "MobiiliTestausTiliAutomaatille", new WilmaLoginListener() {
            @Override
            public void onLogin(Authentication wilmaSession) {
                System.out.println("Login successful");
                System.out.println("SessionId: "+wilmaSession.getSessionId());
                System.out.println("Fetching roles");
                OpenWilma.roles(wilmaSession, new WilmaRolesListener() {
                    @Override
                    public void onFetchRoles(List<Role> roles) {
                        System.out.println("Fetched roles!");
                        System.out.println("");
                        for (Role role : roles) {
                            System.out.println("Name: "+role.getName());
                            System.out.println("School: "+role.getSchool());
                            System.out.println("Slug Id: "+role.getSlug());
                            System.out.println("Id: "+role.getId());
                            System.out.println("Type: "+role.getType());
                            System.out.println("Class: "+new Gson().toJson(role.getClassName()));
                            System.out.println("Class director: "+new Gson().toJson(role.getClassTeacher()));
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