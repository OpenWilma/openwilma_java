package org.openwilma.java.utils;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import org.jetbrains.annotations.NotNull;
import org.openwilma.java.classes.Authentication;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class WilmaCookieJar {

    private static String getDomainName(String url) {
        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            return domain.startsWith("www.") ? domain.substring(4) : domain;
        } catch (Exception e) {
            return url;
        }
    }
    public static CookieJar getWilmaCookieJar(Authentication authentication) {
        return new CookieJar() {
            @Override
            public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
                // We don't save, so ignoring
            }

            @NotNull
            @Override
            public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
                Cookie.Builder cookieBuilder = new Cookie.Builder();
                Cookie wilmaCookie = cookieBuilder.name("Wilma2SID").value(authentication.getSessionId()).path("/").domain(getDomainName(authentication.getWilmaServer().getServerURL())).build();
                List<Cookie> cookies = new ArrayList<>();
                cookies.add(wilmaCookie);
                return cookies;
            }
        };
    }
}
