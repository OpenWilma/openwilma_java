package org.openwilma.java.client;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.openwilma.java.classes.errors.Error;
import org.openwilma.java.classes.errors.ErrorType;
import org.openwilma.java.classes.errors.NetworkError;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AsyncWilmaHttpClient {

    private final OkHttpClient client;

    public AsyncWilmaHttpClient() {
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
        this.client.newCall(getRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                httpClientInterface.onFailed(new NetworkError(e));
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody body = response.body();
                if (body != null) {
                    String content = body.string();
                    httpClientInterface.onResponse(content, response.code());
                } else {
                    httpClientInterface.onFailed(new Error("No content in response", ErrorType.NoContent));
                }
            }
        });
    }
}
