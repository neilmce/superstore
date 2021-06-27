package com.example.jl.remote.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vavr.collection.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JLProduct {
  private final String productId;
  private final String title;
  private final JLPrice price;
  private final List<JLColorSwatch> colorSwatches;

  public JLProduct(@JsonProperty("productId")     String productId,
                   @JsonProperty("title")         String title,
                   @JsonProperty("price")         JLPrice price,
                   @JsonProperty("colorSwatches") List<JLColorSwatch> colorSwatches) {
    this.productId = productId;
    this.title = title;
    this.price = price;
    this.colorSwatches = colorSwatches;
  }

  public String getProductId() {
    return productId;
  }

  public String getTitle() {
    return title;
  }

  public List<JLColorSwatch> getColorSwatches() {
    return colorSwatches;
  }

  public JLPrice getPrice() {
    return price;
  }
}
