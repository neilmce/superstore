package com.example.jl.remote.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JLColorSwatch {
  private final String color;
  private final String basicColor;
  private final String colorSwatchUrl;
  private final String imageUrl;
  private final boolean isAvailable;
  private final String skuId;

  public JLColorSwatch(@JsonProperty("color")          String color,
                       @JsonProperty("basicColor")     String basicColor,
                       @JsonProperty("colorSwatchUrl") String colorSwatchUrl,
                       @JsonProperty("imageUrl")       String imageUrl,
                       @JsonProperty("isAvailable")    boolean isAvailable,
                       @JsonProperty("skuId")          String skuId) {
    this.color = color;
    this.basicColor = basicColor;
    this.colorSwatchUrl = colorSwatchUrl;
    this.imageUrl = imageUrl;
    this.isAvailable = isAvailable;
    this.skuId = skuId;
  }

  public String getColor() {
    return color;
  }

  public String getBasicColor() {
    return basicColor;
  }

  public String getColorSwatchUrl() {
    return colorSwatchUrl;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public String getSkuId() {
    return skuId;
  }
}
