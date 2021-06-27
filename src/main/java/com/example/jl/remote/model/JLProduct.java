package com.example.jl.remote.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vavr.collection.List;

import java.util.Objects;

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

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private String id;
    private String title;
    public Builder withProductId(String id) {
      this.id = id;
      return this;
    }

    public Builder withTitle(String title) {
      this.title = title;
      return this;
    }

    public Builder withColorSwatches() {
      return this;
    }

    public Builder withPrice() {
      return this;
    }

    public JLProduct build() {
      Objects.requireNonNull(id);
      Objects.requireNonNull(title);
      return new JLProduct(id, title, null, List.empty());
    }
  }
}
