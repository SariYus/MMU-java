package com.company.hit.server;

import java.util.Map;

/**
 * stores Request conveniently for use
 * headers : map of request headers
 * body : request body
 *
 * @param <T> : type of request body
 */
public class Request<T>
        extends java.lang.Object
        implements java.io.Serializable {

    private Map<String, String> headers;
    private T body;

    /**
     * constructor
     */
    public Request(Map<String, String> headers, T body) {
        this.headers = headers;
        this.body = body;
    }

    /**
     * @return headers
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * set headers to headers parameter
     */
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    /**
     * @return body
     */
    public T getBody() {
        return body;
    }

    /**
     * set body to body parameter
     */
    public void setBody(T body) {
        this.body = body;
    }

    /**
     * @return string describing the request
     */
    @Override
    public String toString() {
        return getHeaders().toString().split("=")[1].toLowerCase().replace("}", "");
    }
}
