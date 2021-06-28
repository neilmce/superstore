package com.example.jl.remote.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vavr.collection.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JLQueryResponse {
  private final List<JLProduct> products;

  @JsonCreator
  public JLQueryResponse(@JsonProperty("products")        List<JLProduct> products) {
    this.products = products;
  }

  public List<JLProduct> getProducts() {
    return this.products;
  }
}
