package com.orhanobut.wasp;

import android.text.TextUtils;

import com.orhanobut.wasp.utils.LogLevel;

import java.util.Collections;
import java.util.Map;

/**
 * @author alessandro.balocco
 */
public final class WaspResponse {

    private final int statusCode;
    private final int length;
    private final long networkTime;

    private final Object responseObject;
    private final Map<String, String> headers;
    private final String body;
    private final String url;

    private WaspResponse(Builder builder) {
        this.url = builder.getUrl();
        this.statusCode = builder.getStatusCode();
        this.headers = builder.getHeaders();
        this.body = builder.getBody();
        this.length = builder.getLength();
        this.networkTime = builder.getNetworkTime();
        this.responseObject = builder.getResponseObject();
    }

    /**
     * Request URL.
     */
    public String getUrl() {
        return url;
    }

    /**
     * HTTP status code.
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * An unmodifiable map of headers.
     */
    @SuppressWarnings("unused")
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * Response body. May be {@code null}.
     */
    @SuppressWarnings("unused")
    public String getBody() {
        return body;
    }

    /**
     * Response body length.
     */
    @SuppressWarnings("unused")
    public int getLength() {
        return length;
    }

    Object getResponseObject() {
        return responseObject;
    }

    /**
     * Network time elapsed for request to be completed.
     */
    @SuppressWarnings("unused")
    public long getNetworkTime() {
        return networkTime;
    }

    private String getFormattedBody() {
        return body.replace("\n", "").replace("\r", "").replace("\t", "");
    }

    void log(LogLevel logLevel) {
        switch (logLevel) {
            case FULL:
                // Fall Through
            case FULL_REST_ONLY:
                Logger.d("<--- RESPONSE " + statusCode + " " + url);
                if (!headers.isEmpty()) {
                    for (Map.Entry<String, String> entry : headers.entrySet()) {
                        Logger.d("Header - [" + entry.getKey() + ": " + entry.getValue() + "]");
                    }
                }
                Logger.d(TextUtils.isEmpty(body) ? "Body - no body" : "Body - " + getFormattedBody());
                Logger.d("<--- END " + "(Size: " + length + " bytes - Network time: " + networkTime + " ms)");
                break;
            default:
                // Method is called but log level is not meant to log anything
        }
    }


    static class Builder {

        private String url;
        private Map<String, String> headers;
        private String body;
        private Object responseObject;

        private int statusCode;
        private int length;
        private long networkTime;

        WaspResponse build() {
            return new WaspResponse(this);
        }

        String getUrl() {
            return url;
        }

        Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        int getStatusCode() {
            return statusCode;
        }

        Builder setStatusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        Map<String, String> getHeaders() {
            return headers != null ? headers : Collections.<String, String>emptyMap();
        }

        Builder setHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        String getBody() {
            return body;
        }

        Builder setBody(String body) {
            this.body = body;
            return this;
        }

        int getLength() {
            return length;
        }

        Builder setLength(int length) {
            this.length = length;
            return this;
        }

        long getNetworkTime() {
            return networkTime;
        }

        Builder setNetworkTime(long networkTime) {
            this.networkTime = networkTime;
            return this;
        }

        Object getResponseObject() {
            return responseObject;
        }

        Builder setResponseObject(Object object) {
            this.responseObject = object;
            return this;
        }
    }
}
