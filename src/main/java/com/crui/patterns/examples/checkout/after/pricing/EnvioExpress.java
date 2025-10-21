package com.crui.patterns.examples.checkout.after.pricing;

public class EnvioExpress extends PricingDecorator {
  private final double costo;

  public EnvioExpress(Pricing wrappee, double costo) {
    super(wrappee);
    this.costo = costo;
  }

  @Override
  public double total() {
    return super.total() + costo;
  }

  @Override
  public String details() {
    return super.details() + "\nEnvio express: Si (+$" + costo + ")";
  }
}
