package org.openwilma.java.listeners;

import org.openwilma.java.classes.WilmaServer;
import org.openwilma.java.classes.errors.Error;

import java.util.List;

public interface WilmaServersListener {

    void onFetchWilmaServers(List<WilmaServer> wilmaServerList);
    void onFailed(Error error);
}
