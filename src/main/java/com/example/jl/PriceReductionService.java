package com.example.jl;

import com.example.jl.api.Product;
import com.example.jl.remote.RemoteCatalogService;
import com.example.jl.remote.model.JLProduct;
import com.example.jl.remote.model.JLQueryResponse;
import io.vavr.collection.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.function.Predicate;

@Service
public class PriceReductionService {
  private static final Logger LOGGER = LoggerFactory.getLogger(PriceReductionService.class);

  public enum LabelType {
    SHOW_WAS_NOW("ShowWasNow"),
    SHOW_WAS_THEN_NOW("ShowWasThenNow"),
    SHOW_PERC_DISCOUNT("ShowPercDiscount");

    private final String displayName;

    LabelType(String displayName) {
      this.displayName = displayName;
    }

    public static LabelType fromProvidedValue(String labelType) {
      return Arrays.stream(LabelType.values())
          .filter(x -> x.displayName.equals(labelType))
          .findFirst()
          .orElse(SHOW_WAS_NOW);
    }

    public String getDisplayName() {
      return displayName;
    }
  }

  private final RemoteCatalogService remoteCatalogService;
  private final ProductPresentationService productPresentationService;

  @Autowired
  public PriceReductionService(RemoteCatalogService remoteCatalogService, ProductPresentationService productPresentationService) {
    this.remoteCatalogService = remoteCatalogService;
    this.productPresentationService = productPresentationService;
  }

  public List<Product> getProducts(Predicate<JLProduct> filter) {
    return getProducts(LabelType.SHOW_WAS_NOW, filter);
  }

  public List<Product> getProducts(LabelType labelType, Predicate<JLProduct> filter) {
    LOGGER.info("Getting products for labelType:{}", labelType);

    JLQueryResponse rsp = remoteCatalogService.query();
    LOGGER.info("Retrieved {} products", rsp.getProducts().size());

    List<JLProduct> reducedProducts = rsp.getProducts().filter(filter);

    LOGGER.info("Including {} reduced products", reducedProducts.size());

    return reducedProducts
              .map(p -> productPresentationService.formatForApi(p, labelType));
  }
}
