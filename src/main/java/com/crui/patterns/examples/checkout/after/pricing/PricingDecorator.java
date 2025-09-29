package com.crui.patterns.examples.checkout.after.pricing;

public abstract class PricingDecorator implements Pricing {
  protected final Pricing wrappee;

  protected PricingDecorator(Pricing wrappee) {
    this.wrappee = wrappee;
  }

  @Override
  public double total() {
    return wrappee.total();
  }

  @Override
  public String details() {
    return wrappee.details();
  }
}
