package com.example.jl.api;

import io.vavr.collection.List;

public class Product {
  private final String            productId;
  private final String            title;
  private final List<ColorSwatch> colorSwatches;
  private final String            nowPrice;
  private final String            priceLabel;

  public Product(String productId,
                 String title,
                 List<ColorSwatch> colorSwatches,
                 String nowPrice,
                 String priceLabel) {
    this.productId = productId;
    this.title = title;
    this.colorSwatches = colorSwatches;
    this.nowPrice = nowPrice;
    this.priceLabel = priceLabel;
  }

  public String getProductId() {
    return productId;
  }

  public String getTitle() {
    return title;
  }

  public List<ColorSwatch> getColorSwatches() {
    return colorSwatches;
  }

  public String getNowPrice() {
    return nowPrice;
  }

  public String getPriceLabel() {
    return priceLabel;
  }
}
