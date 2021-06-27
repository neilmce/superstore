package com.example.jl;

import com.example.jl.api.Product;
import com.example.jl.remote.RemoteCatalogService;
import com.example.jl.remote.model.JLQueryResponse;
import io.vavr.collection.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceReductionService {
  private final RemoteCatalogService remoteCatalogService;
  private final ProductPresentationService productPresentationService;

  @Autowired
  public PriceReductionService(RemoteCatalogService remoteCatalogService, ProductPresentationService productPresentationService) {
    this.remoteCatalogService = remoteCatalogService;
    this.productPresentationService = productPresentationService;
  }

  public List<Product> getProducts() {
    JLQueryResponse rsp = remoteCatalogService.query();
    return rsp.getProducts().map(productPresentationService::formatForApi);
  }
}
