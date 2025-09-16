package com.crui.patterns.examples.middleware;

public abstract class Middleware implements Handler {

  private Handler next;

  public Middleware(Handler next) {
    this.next = next;
  }

  void handleNext(Request request, Response response) {
    if (next != null) {
      next.process(request, response);
    }
  }
}
