package com.example.jl.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Unit tests for {@link Price}. */
class PriceRangeTest {
  private final Price p1 = Price.of(5, 0);
  private final Price p2 = Price.of(9, 99);

  private final PriceRange r1 = PriceRange.fromTo(p1, p2);
  private final PriceRange r2 = PriceRange.fromTo(p1, p2);
  private final PriceRange r3 = PriceRange.fromTo(p1, Price.of(99, 99));

  @Test void equalsAndHashCode() {

    assertEquals(r1, r2);
    assertNotEquals(r1, r3);
    // Don't care about hashCode.
  }

  @Test void constructOutOfOrder() {
    assertThrows(IllegalArgumentException.class, () -> PriceRange.fromTo("9.99", "1.00"));
  }

  @Test void comparisons() {
    assertTrue(PriceRange.fromTo("1.00", "3.00").isLessThan(PriceRange.fromTo("2.00", "3.00")));
    assertTrue(PriceRange.fromTo("1.00", "3.00").isLessThan(PriceRange.fromTo("2.00", "4.00")));
    assertFalse(PriceRange.fromTo("1.00", "3.00").isLessThan(PriceRange.fromTo("2.00", "2.50")));

    assertFalse(PriceRange.fromTo("1.00", "3.00").isLessThan(PriceRange.fromTo("1.50", "2.50")));
  }

  @Test void constructFromString() {
    var r = PriceRange.fromTo("5.00", "9.99");
    assertEquals(r, r1);
  }
}