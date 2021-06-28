package com.example.jl;

import com.example.jl.api.ColorSwatch;
import com.example.jl.api.Product;
import com.example.jl.remote.model.JLPrice;
import com.example.jl.remote.model.JLProduct;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import io.vavr.collection.List;
import io.vavr.jackson.datatype.VavrModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ProductPresentationServiceTest {
  private final ProductPresentationService presentationService =
      new ProductPresentationService(new RgbColorService(), new CurrencyService());

  private final ObjectReader objectReader = new ObjectMapper()
                                                  .registerModule(new VavrModule())
                                                  .reader()
                                                  .forType(JLProduct.class);

  private JLProduct testProduct;

  @BeforeEach void initTestData() throws JsonProcessingException {
    testProduct = objectReader.readValue("""
    {
      "productId": "5331675",
      "type": "product",
      "title": "Girls' Floral Print Jersey Dress, Blue",
      "price": {
        "was": "9.00",
        "then1": "",
        "then2": "",
        "now": "6.00",
        "uom": "",
        "currency": "GBP"
      },
      "image": "//005121982?",
      "colorSwatches": [
        {
          "color": "Sky blue",
          "basicColor": "Blue",
          "colorSwatchUrl": "//0.42105263157894735,0.7115789473684211,0.10947368421052632,0.10947368421052632&",
          "imageUrl": "//005121982?",
          "isAvailable": true,
          "skuId": "238956452"
        }
      ]
    }
    """);
  }

  @Test void productIdShouldBeCorrect() {
    Product apiProduct = presentationService.formatForApi(testProduct);
    assertEquals("5331675", apiProduct.getProductId());
  }

  @Test void productTitleShouldBeCorrect() {
    Product apiProduct = presentationService.formatForApi(testProduct);
    assertEquals("Girls' Floral Print Jersey Dress, Blue", apiProduct.getTitle());
  }

  @Test void productColorSwatchesShouldBeCorrect() {
    Product apiProduct = presentationService.formatForApi(testProduct);
    List<ColorSwatch> colorSwatches = apiProduct.getColorSwatches();

    assertEquals(1, colorSwatches.size());
    ColorSwatch colorSwatch = colorSwatches.head();

    assertEquals("Sky blue", colorSwatch.getColor());
    assertEquals("0000FF", colorSwatch.getRgbColor());
    assertEquals("238956452", colorSwatch.getSkuId());
  }

  @Test void productNowPrice() {
    Product apiProduct = presentationService.formatForApi(testProduct);
    assertEquals("£6.00", apiProduct.getNowPrice());
  }

  @Test void productNowPriceMoreThanTen() throws JsonProcessingException {
    JLProduct jlProduct = objectReader.readValue(
        """
            {
              "productId": "1234",
              "type": "product",
              "title": "Small dress",
              "price": {
                "was": "",
                "then1": "",
                "then2": "",
                "now": "20.00",
                "uom": "",
                "currency": "USD"
              },
              "image": "//005121982?",
              "colorSwatches": [
                {
                  "color": "Sky blue",
                  "basicColor": "Blue",
                  "colorSwatchUrl": "//0.42105263157894735,0.7115789473684211,0.10947368421052632,0.10947368421052632&",
                  "imageUrl": "//005121982?",
                  "isAvailable": true,
                  "skuId": "238956452"
                }
              ]
            }
            """
    );
    String nowPrice = presentationService.formatNowPrice(jlProduct.getPrice());
    assertEquals("$20", nowPrice);
  }

  @Test void productPriceLabel() {
    fail("not impl'd yet.");
  }
}