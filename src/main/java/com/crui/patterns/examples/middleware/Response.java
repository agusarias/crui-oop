package com.crui.patterns.examples.middleware;

public class Response {
  private int status;
  private String body;

  public Response(int status, String body) {
    this.status = status;
    this.body = body;
  }

  public int getStatus() {
    return status;
  }

  public String getBody() {
    return body;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public String toString() {
    return "Response [status=" + status + ", body=" + body + "]";
  }
}
