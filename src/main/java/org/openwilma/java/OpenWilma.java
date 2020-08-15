package org.openwilma.java;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openwilma.java.classes.WilmaServer;
import org.openwilma.java.classes.errors.Error;
import org.openwilma.java.classes.errors.ErrorType;
import org.openwilma.java.classes.errors.ExceptionError;
import org.openwilma.java.client.WilmaHttpClient;
import org.openwilma.java.config.Config;
import org.openwilma.java.listeners.WilmaServersListener;

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
