package com.example.jl;

import com.example.jl.api.Product;
import com.example.jl.remote.model.JLProduct;
import org.springframework.stereotype.Service;

/**
 * This service is responsible for rendering instances of {@link JLProduct}s into the
 * form used by the published API of this application.
 */
@Service
public class ProductPresentationService {
  public Product formatForApi(JLProduct jlProduct) {
    return new Product(jlProduct.getProductId(), jlProduct.getTitle(), null, null, null);
  }
}
