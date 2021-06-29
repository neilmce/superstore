package com.example.jl.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Unit tests for {@link Price}. */
class PriceTest {
  private final Price p1 = Price.of(6, 30);
  private final Price p2 = Price.of(6, 30);
  private final Price p3 = Price.of(6, 99);

  @Test void priceDifference() {
    assertEquals(Price.of(0, 69), p1.difference(p3));
  }

  @Test void equalsAndHashCode() {

    assertEquals(p1, p2);
    assertNotEquals(p1, p3);
    // Don't care about hashCode.
  }

  @Test void comparisons() {
    assertTrue(p1.isLessThan(p3));
    assertTrue(p1.isLessThanOrEqualTo(p2));
    assertTrue(p1.isGreaterThanOrEqualTo(p2));

    assertTrue(p3.isGreaterThan(p1));
    assertTrue(p3.isGreaterThanOrEqualTo(p1));
  }

  @Test void constructFromString() {
    var p = Price.from("6.99");
    assertEquals(p, p3);
  }

  @Test void constructFromStringErrors() {
    assertThrows(IllegalArgumentException.class, () -> Price.from(""));
    assertThrows(IllegalArgumentException.class, () -> Price.from("6.101"));
    assertThrows(IllegalArgumentException.class, () -> Price.from("6.50.5"));
    assertThrows(IllegalArgumentException.class, () -> Price.from("6"));
    assertThrows(IllegalArgumentException.class, () -> Price.from("-6"));
    assertThrows(IllegalArgumentException.class, () -> Price.from("6.-42"));
    assertThrows(IllegalArgumentException.class, () -> Price.from("$1.99"));
    assertThrows(IllegalArgumentException.class, () -> Price.from("£1.99"));
  }

  @Test void toStringForZeroPenceShouldShowTwoZeroes() {
    assertEquals("1.00", Price.from("1.00").toString());
  }
}