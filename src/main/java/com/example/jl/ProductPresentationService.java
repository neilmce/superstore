package com.example.jl;

import com.example.jl.api.ColorSwatch;
import com.example.jl.api.Product;
import com.example.jl.remote.model.JLColorSwatch;
import com.example.jl.remote.model.JLProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This service is responsible for rendering instances of {@link JLProduct}s into the
 * form used by the published API of this application.
 */
@Service
public class ProductPresentationService {
  private final RgbColorService rgbColorService;

  @Autowired
  public ProductPresentationService(RgbColorService rgbColorService) {
    this.rgbColorService = rgbColorService;
  }

  public Product formatForApi(JLProduct jlProduct) {
    return new Product(jlProduct.getProductId(),
                       jlProduct.getTitle(),
                       jlProduct.getColorSwatches().map(this::formatColorSwatch),
                       null,
                       null);
  }

  private ColorSwatch formatColorSwatch(JLColorSwatch jlColorSwatch) {
    final String rgb = rgbColorService.toRgb(jlColorSwatch.getBasicColor());
    return new ColorSwatch(jlColorSwatch.getColor(), rgb, jlColorSwatch.getSkuId());
  }
}
