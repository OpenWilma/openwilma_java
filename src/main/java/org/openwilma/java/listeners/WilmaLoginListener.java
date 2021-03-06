package org.openwilma.java.listeners;

import org.openwilma.java.classes.Authentication;
import org.openwilma.java.classes.WilmaServer;
import org.openwilma.java.classes.errors.Error;

import java.util.List;

public interface WilmaLoginListener {

    void onLogin(Authentication authentication);
    void onFailed(Error error);
}
