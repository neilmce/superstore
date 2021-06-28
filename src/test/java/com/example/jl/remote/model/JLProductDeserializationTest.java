package com.example.jl.remote.model;

import com.example.jl.common.PriceRange;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import io.vavr.jackson.datatype.VavrModule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** Unit tests for {@link JLProduct} JSON deserialization. */
class JLProductDeserializationTest {

  private final ObjectReader objectReader = new ObjectMapper()
      .registerModule(new VavrModule())
      .reader()
      .forType(JLProduct.class);

  @Test void productWithSimplePrice() throws JsonProcessingException {

    final JLProduct simpleProduct = objectReader.readValue("""
        {
          "productId": "5451564",
          "type": "product",
          "title": "Children's Daisy Print Lead In Jersey Dress, Red",
          "price": {
            "now": "7.50",
            "uom": "",
            "currency": "GBP"
          }
        }
        """);

    assertAll(
        () -> assertEquals("5451564", simpleProduct.getProductId()),
        () -> assertEquals("Children's Daisy Print Lead In Jersey Dress, Red", simpleProduct.getTitle()),
        () -> assertEquals("7.50", simpleProduct.getPrice().getNow().getValue().get()),
        () -> assertEquals("GBP", simpleProduct.getPrice().getCurrency())
    );
  }

  @Test void productWithRangedPrice() throws JsonProcessingException {

    final JLProduct product = objectReader.readValue("""
    {
      "productId": "5285683",
      "type": "product",
      "title": "Striped Jersey Dress, Navy/Multi",
      "price": {
        "was": "34.95",
        "then1": "",
        "then2": "",
        "now": {
          "from": "14.95",
          "to": "34.95"
        },
        "uom": "",
        "currency": "GBP"
      }
    }
    """);

    assertAll(
        () -> assertEquals("5285683", product.getProductId()),
        () -> assertEquals("Striped Jersey Dress, Navy/Multi", product.getTitle()),
        () -> assertEquals("34.95", product.getPrice().getWas().getValue().get()),
        () -> assertEquals(PriceRange.fromTo("14.95", "34.95"), product.getPrice().getNow().getValue().getLeft()),
        () -> assertEquals("GBP", product.getPrice().getCurrency())
    );
  }
}
