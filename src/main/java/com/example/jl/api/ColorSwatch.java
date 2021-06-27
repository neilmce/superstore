package com.example.jl.api;

public class ColorSwatch {
  private final String color;
  private final String rgbColor;
  private final String skuId;

  public ColorSwatch(String color, String rgbColor, String skuId) {
    this.color = color;
    this.rgbColor = rgbColor;
    this.skuId = skuId;
  }

  public String getColor() {
    return this.color;
  }

  public String getRgbColor() {
    return this.rgbColor;
  }

  public String getSkuId() {
    return this.skuId;
  }
}
