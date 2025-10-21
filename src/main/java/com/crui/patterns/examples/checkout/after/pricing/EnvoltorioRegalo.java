package com.crui.patterns.examples.checkout.after.pricing;

public class EnvoltorioRegalo extends PricingDecorator {
  private final double costo;

  public EnvoltorioRegalo(Pricing wrappee, double costo) {
    super(wrappee);
    this.costo = costo;
  }

  @Override
  public double total() {
    return super.total() + costo;
  }

  @Override
  public String details() {
    return super.details() + "\nEnvoltorio regalo: Si (+$" + costo + ")";
  }
}
