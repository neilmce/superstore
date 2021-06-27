package com.example.jl.remote.model;

import java.util.Objects;

public class PriceRange {
  private final String from;
  private final String to;

  public PriceRange(String from,
                    String to) {
    this.from = from;
    this.to = to;
  }

  public static PriceRange fromTo(String from, String to) {
    return new PriceRange(from, to);
  }

  public String getFrom() {
    return from;
  }

  public String getTo() {
    return to;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PriceRange that = (PriceRange) o;
    return Objects.equals(from, that.from) && Objects.equals(to, that.to);
  }

  @Override
  public int hashCode() {
    return Objects.hash(from, to);
  }
}
