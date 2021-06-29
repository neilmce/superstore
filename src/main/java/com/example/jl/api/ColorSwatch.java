package com.example.jl.api;

/**
 * A simplified Color Swatch representation for returning over the REST API.
 */
public class ColorSwatch {
  private final String color;
  private final String rgbColor;
  private final String skuId;

  public ColorSwatch(String color, String rgbColor, String skuId) {
    this.color = color;
    this.rgbColor = rgbColor;
    this.skuId = skuId;
  }

  /** A color name string. */
  public String getColor() {
    return this.color;
  }

  /** an RGB color as a 6 digit hex string e.g. red is FF0000. */
  public String getRgbColor() {
    return this.rgbColor;
  }

  public String getSkuId() {
    return this.skuId;
  }
}
