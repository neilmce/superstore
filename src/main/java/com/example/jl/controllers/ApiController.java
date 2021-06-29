package com.example.jl.controllers;

import com.example.jl.PriceReductionComparator;
import com.example.jl.ProductService;
import com.example.jl.ProductService.LabelType;
import com.example.jl.ReducedProductFilter;
import com.example.jl.api.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This class defines the REST API for this application.
 */
@RestController
public class ApiController {

  private final ProductService productService;

  @Autowired
  public ApiController(ProductService productService) {
    this.productService = productService;
  }

  /**
   *
   * @param labelType an optional query parameter which tailors the response's price label field.
   * @return a List of products, simplified compared to the response from the remote catalog service.
   */
  @RequestMapping(value = "/products", produces = "application/json;charset=UTF-8")
  public ResponseEntity<List<Product>> getReducedProducts(@RequestParam(required = false) String labelType) {
    var lt = LabelType.fromProvidedValue(labelType);
    return ResponseEntity.ok(
        productService.getProducts(lt,
                                          new ReducedProductFilter(),
                                          new PriceReductionComparator()
        ).asJava()
    );
  }
}
