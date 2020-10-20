package org.openwilma.java.listeners;

import org.openwilma.java.classes.Authentication;
import org.openwilma.java.classes.errors.Error;

/**
 * When onFetchProfile is called, Authentication object's user variable is inserted with User object, and that's the profile.
 */
public interface WilmaProfileListener {
    void onFetchProfile(Authentication authentication);
    void onFailed(Error error);
}
