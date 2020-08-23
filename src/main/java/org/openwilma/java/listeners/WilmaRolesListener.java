package org.openwilma.java.listeners;

import org.openwilma.java.classes.Authentication;
import org.openwilma.java.classes.Role;
import org.openwilma.java.classes.errors.Error;

import java.util.List;

public interface WilmaRolesListener {

    void onFetchRoles(List<Role> roles);
    void onFailed(Error error);
}
