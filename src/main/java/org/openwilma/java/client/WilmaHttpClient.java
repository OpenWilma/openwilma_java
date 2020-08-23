package org.openwilma.java.client;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.openwilma.java.classes.Authentication;
import org.openwilma.java.classes.errors.Error;
import org.openwilma.java.classes.errors.ErrorType;
import org.openwilma.java.classes.errors.NetworkError;
import org.openwilma.java.utils.WilmaCookieJar;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class WilmaHttpClient {

    private final OkHttpClient client;

    public WilmaHttpClient() {
        this.client = new OkHttpClient().newBuilder().callTimeout(60, TimeUnit.SECONDS).build();
    }

    public WilmaHttpClient(Authentication authentication) {
        this.client = new OkHttpClient().newBuilder().cookieJar(WilmaCookieJar.getWilmaCookieJar(authentication)).callTimeout(60, TimeUnit.SECONDS).build();
    }

    public WilmaHttpClient(boolean followRedirects) {
        this.client = new OkHttpClient().newBuilder().followRedirects(followRedirects).followSslRedirects(followRedirects).callTimeout(60, TimeUnit.SECONDS).build();
    }

    public interface HttpClientInterface {
        void onResponse(String response, int status);
        void onRawResponse(Response response);
        void onFailed(Error error);
    }



    public void getRequest(String url, HttpClientInterface httpClientInterface) {
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url);

        // Making the request
        Request getRequest = requestBuilder.build();
        try {
            Response response = this.client.newCall(getRequest).execute();
            ResponseBody body = response.body();
            if (body != null) {
                String content = body.string();
                httpClientInterface.onResponse(content, response.code());
            } else {
                httpClientInterface.onFailed(new Error("No content in response", ErrorType.NoContent));
            }
        } catch (IOException e) {
            httpClientInterface.onFailed(new NetworkError(e));
        }
    }

    public void getRawRequest(String url, HttpClientInterface httpClientInterface) {
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url);

        // Making the request
        Request getRequest = requestBuilder.build();
        try {
            Response response = this.client.newCall(getRequest).execute();
            httpClientInterface.onRawResponse(response);
        } catch (IOException e) {
            httpClientInterface.onFailed(new NetworkError(e));
        }
    }

    public void postRequest(String url, RequestBody requestBody, HttpClientInterface httpClientInterface) {
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder
                .post(requestBody)
                .url(url);

        // Making the request
        Request getRequest = requestBuilder.build();
        try {
            Response response = this.client.newCall(getRequest).execute();
            ResponseBody body = response.body();
            if (body != null) {
                String content = body.string();
                httpClientInterface.onResponse(content, response.code());
            } else {
                httpClientInterface.onFailed(new Error("No content in response", ErrorType.NoContent));
            }
        } catch (IOException e) {
            httpClientInterface.onFailed(new NetworkError(e));
        }
    }

    public void postRawRequest(String url, RequestBody requestBody, HttpClientInterface httpClientInterface) {
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder
                .post(requestBody)
                .url(url);

        // Making the request
        Request getRequest = requestBuilder.build();
        try {
            Response response = this.client.newCall(getRequest).execute();
            httpClientInterface.onRawResponse(response);
        } catch (IOException e) {
            httpClientInterface.onFailed(new NetworkError(e));
        }
    }


}
