package com.crui.patterns.examples.middleware;

public class ErrorsMiddleware extends Middleware {
  public ErrorsMiddleware(Handler next) {
    super(next);
  }

  @Override
  public void process(Request request, Response response) {
    try {
      handleNext(request, response);
    } catch (Exception e) {
      response.setStatus(500);
      response.setBody("Internal server error");
      System.out.println("Internal server error");
      return;
    }
  }
}
