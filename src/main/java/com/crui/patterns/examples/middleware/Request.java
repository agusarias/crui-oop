package com.crui.patterns.examples.middleware;

public class Request {
  private String method;
  private String url;
  private String token;
  private String body;

  public Request(String method, String url, String token, String body) {
    this.method = method;
    this.url = url;
    this.token = token;
    this.body = body;
  }

  public String getMethod() {
    return method;
  }

  public String getUrl() {
    return url;
  }

  public String getToken() {
    return token;
  }

  @Override
  public String toString() {
    return "Request [method="
        + method
        + ", url="
        + url
        + ", token="
        + token
        + ", body="
        + body
        + "]";
  }
}
