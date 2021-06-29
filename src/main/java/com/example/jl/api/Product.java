package com.example.jl.api;

import java.util.List;

/** A simplified representation of a product, as returned by the REST API. */
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

  /** This product's ID. */
  public String getProductId() {
    return productId;
  }

  /** The title of this product. */
  public String getTitle() {
    return title;
  }

  /** A list of color swatches for this product. */
  public List<ColorSwatch> getColorSwatches() {
    return colorSwatches;
  }

  /** Gets the current price or price range of this product. */
  public String getNowPrice() {
    return nowPrice;
  }

  /** Gets a descriptive label for the price, highlighting recent price reductions for this product. */
  public String getPriceLabel() {
    return priceLabel;
  }
}
