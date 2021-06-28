package com.example.jl.common;

import io.vavr.Tuple2;

import java.util.Objects;

import static java.lang.String.format;

/**
 * This class represents a fixed range of prices from a lower price to an upper.
 * Ranges are also {@code Comparable}, where a range will only be "less than" another
 * range if its allowed values are all less than or equal to the other's.
 *
 * e.g. 1.00 - 3.00 is less than 2.00 - 3.00.
 *      1.00 - 3.00 is also less than 2.00 - 4.00.
 *      But 1.00 - 3.00 is not less than 2.00 - 2.50.
 *
 *      1.00 - 3.00 is not less than or greater than 1.50 - 2.50
 *
 */
public class PriceRange implements Comparable<PriceRange> {
  private final Price from;
  private final Price to;

  public PriceRange(Price from,
                    Price to) {
    if (from.isGreaterThan(to)) {
      throw new IllegalArgumentException(format("PriceRanges must be ascending: %s..%s", from, to));
    }
    this.from = from;
    this.to = to;
  }

  public static PriceRange fromTo(String from, String to) {
    return PriceRange.fromTo(Price.from(from), Price.from(to));
  }

  public static PriceRange fromTo(Price from, Price to) {
    return new PriceRange(from, to);
  }

  public Price getFrom() {
    return from;
  }

  public Price getTo() {
    return to;
  }

  @Override public String toString() {
    return format("%s..%s", from, to);
  }

  public Tuple2<String, String> toDisplayStrings() {
    return new Tuple2<>(from.toDisplayString(), to.toDisplayString());
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

  @Override
  public int compareTo(PriceRange that) {
    if (this.equals(that)) {
      return 0;
    }
    else if (this.from.isLessThan(that.from) && this.to.isLessThanOrEqualTo(that.to)) {
      return -1;
    }
    else if (this.from.isGreaterThanOrEqualTo(that.from) && this.to.isGreaterThan(that.to)) {
      return 1;
    }
    else {
      return 0;
    }
  }

  public boolean isLessThanOrEqualTo(PriceRange that) {
    return this.compareTo(that) <= 0;
  }

  public boolean isLessThan(PriceRange that) {
    return this.compareTo(that) < 0;
  }

  public boolean isGreaterThan(PriceRange that) {
    return this.compareTo(that) > 0;
  }

  public boolean isGreaterThanOrEqualTo(PriceRange that) {
    return this.compareTo(that) >= 0;
  }

}
