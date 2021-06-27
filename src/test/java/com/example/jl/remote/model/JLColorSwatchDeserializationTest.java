package com.example.jl.remote.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import io.vavr.jackson.datatype.VavrModule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JLColorSwatchDeserializationTest {

  private final ObjectReader objectReader = new ObjectMapper()
      .registerModule(new VavrModule())
      .reader()
      .forType(JLColorSwatch.class);

  @Test void simpleColorSwatch() throws JsonProcessingException {

    final JLColorSwatch colorSwatch = objectReader.readValue("""
        {
          "color": "",
          "basicColor": "Red",
          "colorSwatchUrl": "//0.1,0.2,0.3",
          "imageUrl": "//005181829?",
          "isAvailable": true,
          "skuId": "239096077"
        }
        """);

    assertAll(
        () -> assertEquals("", colorSwatch.getColor()),
        () -> assertEquals("Red", colorSwatch.getBasicColor()),
        () -> assertEquals("//0.1,0.2,0.3", colorSwatch.getColorSwatchUrl()),
        () -> assertEquals("//005181829?", colorSwatch.getImageUrl()),
        () -> assertTrue(colorSwatch.isAvailable()),
        () -> assertEquals("239096077", colorSwatch.getSkuId())
    );
  }
}
