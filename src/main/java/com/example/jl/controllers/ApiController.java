package com.example.jl.controllers;

import com.example.jl.PriceReductionService;
import com.example.jl.PriceReductionService.LabelType;
import com.example.jl.ReducedProductFilter;
import com.example.jl.api.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiController {

  private final PriceReductionService priceReductionService;

  @Autowired
  public ApiController(PriceReductionService priceReductionService) {
    this.priceReductionService = priceReductionService;
  }

  @RequestMapping(value = "/", produces = "application/json;charset=UTF-8")
  public ResponseEntity<List<Product>> getReducedProducts(@RequestParam(required = false) String labelType) {
    var lt = LabelType.fromProvidedValue(labelType);
    return ResponseEntity.ok(priceReductionService.getProducts(lt, new ReducedProductFilter()).asJava());
  }
}
