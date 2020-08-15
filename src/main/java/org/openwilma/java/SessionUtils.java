package org.openwilma.java;

import org.openwilma.java.config.Config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SessionUtils {

    public static String parseSessionCookie(String cookieValue) {
        Pattern sessionPattern = Pattern.compile(Config.sessionRegex);
        Matcher valueMatcher = sessionPattern.matcher(cookieValue);
        if (valueMatcher.find()) {
            return valueMatcher.group(2);
        }
        return null;
    }
}
