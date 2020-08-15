package org.openwilma.java;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.FormBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openwilma.java.classes.WilmaServer;
import org.openwilma.java.classes.errors.*;
import org.openwilma.java.classes.errors.Error;
import org.openwilma.java.classes.responses.JSONErrorResponse;
import org.openwilma.java.classes.responses.SessionResponse;
import org.openwilma.java.client.WilmaHttpClient;
import org.openwilma.java.config.Config;
import org.openwilma.java.listeners.WilmaLoginListener;
import org.openwilma.java.listeners.WilmaServersListener;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class OpenWilma {

    public static void wilmaServers(WilmaServersListener listener) {
        WilmaHttpClient httpClient = new WilmaHttpClient();
        httpClient.getRequest(Config.wilmaServersURL, new WilmaHttpClient.HttpClientInterface() {
            @Override
            public void onResponse(String response, int status) {
                if (isJSONValid(response)) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        List<WilmaServer> servers = new ArrayList<>();
                        if (jsonObject.has("wilmat")) {
                            String wilmatArrJSON = jsonObject.getJSONArray("wilmat").toString();
                            servers.addAll(new Gson().fromJson(wilmatArrJSON, TypeToken.getParameterized(List.class, WilmaServer.class).getType()));
                        }
                        listener.onFetchWilmaServers(servers);
                    } catch (JSONException e) {
                        listener.onFailed(new ExceptionError(e));
                    }
                } else {
                    listener.onFailed(new Error("Invalid JSON", ErrorType.InvalidContent));
                }
            }

            @Override
            public void onRawResponse(Response response) {
                // Not used
            }

            @Override
            public void onFailed(Error error) {
                listener.onFailed(error);
            }
        });
    }

    private static String buildUrl(WilmaServer wilmaServer, String path) {
        String serverUrl = wilmaServer.getServerURL();
        if (serverUrl.endsWith("/"))
            serverUrl = serverUrl + path;
        else
            serverUrl = serverUrl + "/" + path;
        return serverUrl;
    }

    public static void login(WilmaServer wilmaServer, String username, String password, WilmaLoginListener listener) {
        WilmaHttpClient loginHttpClient = new WilmaHttpClient(false);
        WilmaHttpClient sessionHttpClient = new WilmaHttpClient();

        // Fetching the session
        sessionHttpClient.getRequest(buildUrl(wilmaServer, "index_json"), new WilmaHttpClient.HttpClientInterface() {
            @Override
            public void onResponse(String response, int status) {
                String session = null;

                if (isJSONValid(response)) {
                    SessionResponse sessionResponse = new Gson().fromJson(response, SessionResponse.class);
                    if (sessionResponse.getSessionId() != null)
                        session = sessionResponse.getSessionId();
                    else {
                        listener.onFailed(new Error("SessionId is missing", ErrorType.NoContent));
                        return;
                    }
                } else {
                    listener.onFailed(new Error("Invalid JSON", ErrorType.InvalidContent));
                    return;
                }

                FormBody.Builder formBuilder = new FormBody.Builder();
                // Login parameters
                formBuilder.add("Login", username);
                formBuilder.add("Password", password);
                formBuilder.add("SESSIONID", session);
                formBuilder.add("CompleteJson", "");
                formBuilder.add("format", "json");

                // Logging in
                loginHttpClient.postRawRequest(buildUrl(wilmaServer, "index_json"), formBuilder.build(), new WilmaHttpClient.HttpClientInterface() {
                    @Override
                    public void onResponse(String response, int status) {
                        // not used
                    }

                    @Override
                    public void onRawResponse(Response response) {
                        try {
                            ResponseBody body = response.body();
                            if (body != null) {
                                String content = body.string();
                                if (isJSONValid(content)) {
                                    // If valid json, error must be occurred
                                    try {
                                        JSONErrorResponse errorResponse = new Gson().fromJson(content, JSONErrorResponse.class);
                                        if (errorResponse != null && errorResponse.getWilmaError() != null) {
                                            listener.onFailed(errorResponse.getWilmaError());
                                            return;
                                        }
                                    } catch (JSONException e) {
                                        listener.onFailed(new ExceptionError(e));
                                        return;
                                    }
                                }
                            }
                            if (response.headers().names().contains("Location")) {
                                String locationValue = response.headers().get("Location");
                                try {
                                    URL url = new URL(locationValue);
                                    if (url.getPath().equals("/")) {
                                        if (url.getQuery().contains("loginfailed")) {
                                            listener.onFailed(new CredentialsError());
                                        } else if (url.getQuery().contains("checkcookie")) {
                                            // TODO get session cookies
                                        }
                                    }
                                } catch (Exception e) {
                                    listener.onFailed(new ExceptionError(e));
                                }
                            } else {
                                listener.onFailed(new Error("Login status is missing", ErrorType.NoContent));
                            }
                        } catch (IOException e) {
                            listener.onFailed(new NetworkError(e));
                        }
                    }

                    @Override
                    public void onFailed(Error error) {
                        listener.onFailed(error);
                    }
                });
            }

            @Override
            public void onRawResponse(Response response) {

            }

            @Override
            public void onFailed(Error error) {
                listener.onFailed(error);
            }
        });


    }

    private static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

}
