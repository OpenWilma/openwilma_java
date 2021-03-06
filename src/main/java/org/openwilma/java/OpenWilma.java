package org.openwilma.java;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.FormBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openwilma.java.classes.Authentication;
import org.openwilma.java.classes.Role;
import org.openwilma.java.classes.WilmaServer;
import org.openwilma.java.classes.errors.Error;
import org.openwilma.java.classes.errors.*;
import org.openwilma.java.classes.responses.JSONErrorResponse;
import org.openwilma.java.classes.responses.SessionResponse;
import org.openwilma.java.client.WilmaHttpClient;
import org.openwilma.java.config.Config;
import org.openwilma.java.listeners.WilmaLoginListener;
import org.openwilma.java.listeners.WilmaProfileListener;
import org.openwilma.java.listeners.WilmaRolesListener;
import org.openwilma.java.listeners.WilmaServersListener;
import org.openwilma.java.parser.ParseUtils;
import org.openwilma.java.parser.ProfileParser;
import org.openwilma.java.parser.RoleParser;
import org.openwilma.java.utils.SessionUtils;

import java.io.IOException;
import java.net.URI;
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

    private static String buildUrlWithJsonFormat(WilmaServer wilmaServer, String path) {
        String serverUrl = wilmaServer.getServerURL();
        if (serverUrl.endsWith("/"))
            serverUrl = serverUrl + path;
        else
            serverUrl = serverUrl + "/" + path;
        try {
            URI oldUri = new URI(serverUrl);
            String newQuery = oldUri.getQuery();
            if (newQuery == null) {
                newQuery = Config.jsonQuery;
            } else {
                newQuery += "&" + Config.jsonQuery;
            }
            URI patchedUri = new URI(oldUri.getScheme(), oldUri.getAuthority(),
                    oldUri.getPath(), newQuery, oldUri.getFragment());
            return patchedUri.toString();
        } catch (Exception ignored) {
            return serverUrl;
        }
    }

    public static void login(WilmaServer wilmaServer, String username, String password, WilmaLoginListener listener) {
        WilmaHttpClient loginHttpClient = new WilmaHttpClient(false);
        WilmaHttpClient sessionHttpClient = new WilmaHttpClient();

        // Fetching the session
        sessionHttpClient.getRequest(buildUrl(wilmaServer, "index_json"), new WilmaHttpClient.HttpClientInterface() {
            @Override
            public void onResponse(String response, int status) {
                String session;

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
                                if (locationValue == null) {
                                    listener.onFailed(new Error("No redirect header", ErrorType.InvalidContent));
                                    return;
                                }
                                try {
                                    URL url = new URL(locationValue);
                                    if (url.getPath().equals("/") || url.getPath().contains("/!")) {
                                        if (url.getQuery().contains("loginfailed")) {
                                            listener.onFailed(new CredentialsError());
                                        } else if (url.getQuery().contains("checkcookie")) {
                                            if (response.headers().names().contains("Set-Cookie")) {
                                                String sessionId = null;
                                                List<String> sessionHeaders = response.headers("Set-Cookie");
                                                for (String cookie : sessionHeaders) {
                                                    if (cookie.contains("Wilma2SID=")) {
                                                        sessionId = SessionUtils.parseSessionCookie(cookie);
                                                        break;
                                                    }
                                                }
                                                if (sessionId != null) {
                                                    Authentication authentication = new Authentication(wilmaServer, sessionId, false);
                                                    updateMainProfile(authentication, new WilmaProfileListener() {
                                                        @Override
                                                        public void onFetchProfile(Authentication authentication) {
                                                            listener.onLogin(authentication);
                                                        }

                                                        @Override
                                                        public void onFailed(Error error) {
                                                            listener.onFailed(error);
                                                        }
                                                    });
                                                } else
                                                    listener.onFailed(new Error("Unable to parse session cookie", ErrorType.InvalidContent));
                                            } else
                                                listener.onFailed(new Error("Session cookies missing", ErrorType.NoContent));
                                        }
                                    } else if (url.getPath().equals("/mfa")) {
                                        // MFA enabled, error
                                        listener.onFailed(new MFAError());
                                    } else
                                        listener.onFailed(new Error("Unrecognized redirection: "+url.getPath(), ErrorType.Unknown));
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
               // not used
            }

            @Override
            public void onFailed(Error error) {
                listener.onFailed(error);
            }
        });


    }

    public static void updateMainProfile(Authentication authentication, WilmaProfileListener listener) {
        // Updating user
        WilmaHttpClient sessionHttpClient = new WilmaHttpClient(authentication);
        sessionHttpClient.getRawRequest(buildUrlWithJsonFormat(authentication.getWilmaServer(), ""), new WilmaHttpClient.HttpClientInterface() {
            @Override
            public void onResponse(String response, int status) {

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
                                }
                            } catch (JSONException e) {
                                listener.onFailed(new ExceptionError(e));
                            }
                        } else {
                            // Updating user
                            authentication.setUser(ProfileParser.parseProfile(content));
                            authentication.setRequiresRole(ProfileParser.roleSelectorExists(content));
                            listener.onFetchProfile(authentication);
                        }
                    } else
                        listener.onFailed(new Error("Cannot load response", ErrorType.NoContent));

                } catch (IOException e) {
                    listener.onFailed(new NetworkError(e));
                }
            }

            @Override
            public void onFailed(Error error) {

            }
        });
    }

    public static void roles(Authentication authentication, WilmaRolesListener listener) {
        WilmaHttpClient sessionHttpClient = new WilmaHttpClient(authentication);
        sessionHttpClient.getRawRequest(buildUrlWithJsonFormat(authentication.getWilmaServer(), ""), new WilmaHttpClient.HttpClientInterface() {
            @Override
            public void onResponse(String response, int status) {

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
                                }
                            } catch (JSONException e) {
                                listener.onFailed(new ExceptionError(e));
                            }
                        } else {
                            // Proceed to parsing
                            // Updating user
                            authentication.setUser(ProfileParser.parseProfile(content));

                            List<Role> roles = RoleParser.parseRoles(content);
                            listener.onFetchRoles(roles);
                        }
                    } else
                        listener.onFailed(new Error("Cannot load response", ErrorType.NoContent));

                } catch (IOException e) {
                    listener.onFailed(new NetworkError(e));
                }
            }

            @Override
            public void onFailed(Error error) {

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
