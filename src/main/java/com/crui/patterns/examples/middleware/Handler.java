package com.crui.patterns.examples.middleware;

public interface Handler {
  void process(Request request, Response response);
}
