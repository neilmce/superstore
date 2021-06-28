package com.example.jl;

import com.example.jl.api.Product;
import com.example.jl.remote.RemoteCatalogService;
import com.example.jl.remote.model.JLQueryResponse;
import io.vavr.collection.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceReductionService {

  public enum LabelType {
    SHOW_WAS_NOW("ShowWasNow"),
    SHOW_WAS_THEN_NOW("ShowWasThenNow"),
    SHOW_PERC_DISCOUNT("ShowPercDiscount");

    private final String value;

    LabelType(String value) {
      this.value = value;
    }
  }

  private final RemoteCatalogService remoteCatalogService;
  private final ProductPresentationService productPresentationService;

  @Autowired
  public PriceReductionService(RemoteCatalogService remoteCatalogService, ProductPresentationService productPresentationService) {
    this.remoteCatalogService = remoteCatalogService;
    this.productPresentationService = productPresentationService;
  }

  public List<Product> getProducts() {
    return getProducts(LabelType.SHOW_WAS_NOW);
  }

  public List<Product> getProducts(LabelType labelType) {
    JLQueryResponse rsp = remoteCatalogService.query();
    return rsp.getProducts().map(productPresentationService::formatForApi);
  }
}
