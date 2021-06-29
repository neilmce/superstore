package com.example.jl;

import com.example.jl.api.Product;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class ApplicationMainTests {

  @Autowired private ProductService productService;

  @Test
  void contextLoads() {
    List<Product> products = productService.getProducts(new ReducedProductFilter(), new PriceReductionComparator());

    products.forEach(p ->
        assertAll(
          () -> assertFalse(p.getProductId().isBlank()),
          () -> assertFalse(p.getTitle().isBlank())
        ));
    System.out.println(products);
  }

}
