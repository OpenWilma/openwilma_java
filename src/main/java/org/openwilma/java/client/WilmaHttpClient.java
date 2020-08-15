package org.openwilma.java.client;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.openwilma.java.classes.errors.Error;
import org.openwilma.java.classes.errors.ErrorType;
import org.openwilma.java.classes.errors.NetworkError;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class WilmaHttpClient {

    private final OkHttpClient client;

    public WilmaHttpClient() {
        this.client = new OkHttpClient().newBuilder().callTimeout(60, TimeUnit.SECONDS).build();
    }

    public interface HttpClientInterface {
        void onResponse(String response, int status);
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
}
