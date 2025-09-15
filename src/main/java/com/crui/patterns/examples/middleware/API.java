package com.crui.patterns.examples.middleware;

public class API {

  public static void main(String[] args) {
    Handler handler =
        new Handler() {
          @Override
          public void process(Request request, Response response) {
            response.setStatus(200);
            response.setBody("### Hello, world! ###");
            if (request.getMethod().equals("POST")) {
              throw new RuntimeException("Method not allowed");
            }
          }
        };

    // chain of responsibility
    Handler middleware = new LoggingMiddleware(new ErrorsMiddleware(new AuthMiddleware(handler)));

    Request request = new Request("GET", "/api/v1/users", "valid-token", "");
    Response response = new Response(200, "");
    middleware.process(request, response);

    // System.out.println(response.getBody());

    Request invalidRequest = new Request("GET", "/api/v1/users", "invalid-token", "");
    Response invalidResponse = new Response(200, "");
    middleware.process(invalidRequest, invalidResponse);

    Request invalidMethodRequest = new Request("POST", "/api/v1/users", "valid-token", "");
    Response invalidMethodResponse = new Response(200, "");
    middleware.process(invalidMethodRequest, invalidMethodResponse);
  }
}
