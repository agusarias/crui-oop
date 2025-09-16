package com.crui.patterns.examples.middleware;

public class AuthMiddleware extends Middleware {
  private String validToken;

  public AuthMiddleware(Handler next) {
    super(next);
    this.validToken = "valid-token";
  }

  @Override
  public void process(Request request, Response response) {
    if (request.getToken() != this.validToken) {
      response.setStatus(401);
      System.out.println("Invalid token");
      return;
    } else {
      System.out.println("Valid token");
    }
    handleNext(request, response);
  }
}
