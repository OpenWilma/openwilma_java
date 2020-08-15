package org.openwilma.java.listeners;

import org.openwilma.java.classes.WilmaServer;
import org.openwilma.java.classes.errors.Error;

import java.util.List;

public interface WilmaLoginListener {

    void onLogin(String wilmaSession);
    void onFailed(Error error);
}
