package com.example.jl;

import com.example.jl.api.Product;
import com.example.jl.remote.model.JLProduct;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductPresentationServiceTest {
  private final ProductPresentationService presentationService = new ProductPresentationService();

  private final JLProduct testProduct = JLProduct.builder()
                                                   .withProductId("id")
                                                   .withTitle("title")
                                                   .withColorSwatches()
                                                   .withPrice()
                                                 .build();

  @Test void productIdShouldBeCorrect() {
    Product apiProduct = presentationService.formatForApi(testProduct);
    assertEquals("id", apiProduct.getProductId());
  }

  @Test void productTitleShouldBeCorrect() {
    Product apiProduct = presentationService.formatForApi(testProduct);
    assertEquals("title", apiProduct.getTitle());
  }

  @Test void productColorSwatches() {
    fail("not impl'd yet.");
  }

  @Test void productNowPrice() {
    fail("not impl'd yet.");
  }

  @Test void productPriceLabel() {
    fail("not impl'd yet.");
  }
}