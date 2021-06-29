package com.example.jl.common;

import java.util.Objects;

import static java.lang.String.format;

/** This class represents a two-part price for something e.g. $1.99 or £10.00. */
public class Price implements Comparable<Price> {
  private final int major;
  private final int minor;

  private Price(int major, int minor) {
    assertNotNegative(major);
    assertNotNegative(minor);
    if (minor > 99) {
      throw new IllegalArgumentException(format("Cannot construct price with negative minor component: '%d'", minor));
    }
    this.major = major;
    this.minor = minor;
  }

  /**
   * Creates a new {@link Price} object.
   * @param major the major price element, e.g. pounds.
   * @param minor the minor price element, r.g. pence.
   * @return a new Price object.
   */
  public static Price of(int major, int minor) {
    return new Price(major, minor);
  }

  /**
   * Creates a new {@link Price} object.
   * @param s a string e.g. "6.99".
   * @throws IllegalArgumentException if the string cannot be unambiguously turned into a price.
   */
  public static Price from(String s) {
    var components = s.split("\\.");
    if (components.length != 2) {
      throw new IllegalArgumentException(format("Only prices with 2 components are supported: '%s'", s));
    }
    try {
      return Price.of(Integer.parseInt(components[0]), Integer.parseInt(components[1]));
    }
    catch (NumberFormatException nfe) {
      throw new IllegalArgumentException(format("Cannot construct price from '%s'", s));
    }
  }

  private void assertNotNegative(int n) {
    if (n < 0) {
      throw new IllegalArgumentException(format("Price number cannot be negative: '%d'", n));
    }
  }

  /**
   * @return the major price component e.g. pounds.
   */
  public int getMajor() {
    return major;
  }

  /**
   * @return the minor price component e.g. pennies.
   */
  public int getMinor() {
    return minor;
  }

  @Override public int compareTo(Price that) {
    var majorComp = Integer.compare(this.major, that.major);
    if (majorComp == 0) {
      return Integer.compare(this.minor, that.minor);
    }
    else {
      return majorComp;
    }
  }

  public boolean isLessThanOrEqualTo(Price that) {
    return this.compareTo(that) <= 0;
  }

  public boolean isLessThan(Price that) {
    return this.compareTo(that) < 0;
  }

  public boolean isGreaterThan(Price that) {
    return this.compareTo(that) > 0;
  }

  public boolean isGreaterThanOrEqualTo(Price that) {
    return this.compareTo(that) >= 0;
  }

  @Override public String toString() {
    return format("%d.%02d", major, minor);
  }

  public String toDisplayString() {
    var needsTruncating = minor == 0 && major >= 10;

    if (needsTruncating) {
      return Integer.toString(major);
    }
    else {
      return toString();
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    var price = (Price) o;
    return major == price.major && minor == price.minor;
  }

  @Override
  public int hashCode() {
    return Objects.hash(major, minor);
  }

  public Price difference(Price that) {
    var thisPriceInPennies = this.major * 100 + this.minor;
    var thatPriceInPennies = that.major * 100 + that.minor;

    var differenceInPennies = Math.abs(thisPriceInPennies - thatPriceInPennies);

    return Price.of(differenceInPennies / 100, differenceInPennies % 100);
  }
}
