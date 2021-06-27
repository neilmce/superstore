package com.example.jl.remote.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.vavr.collection.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JLQueryResponse {
  private final boolean showInStockOnly;
  private final List<JLProduct> products;
  private final int results;
  private final int pagesAvailable;

  @JsonCreator
  public JLQueryResponse(@JsonProperty("showInStockOnly") boolean showInStockOnly,
                         @JsonProperty("products")        List<JLProduct> products,
                         @JsonProperty("results")         int results,
                         @JsonProperty("pagesAvailable")  int pagesAvailable) {
    this.showInStockOnly = showInStockOnly;
    this.products = products;
    this.results = results;
    this.pagesAvailable = pagesAvailable;
  }

  public List<JLProduct> getProducts() {
    return this.products;
  }
}
