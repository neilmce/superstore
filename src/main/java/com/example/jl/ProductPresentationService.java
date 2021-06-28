package com.example.jl;

import com.example.jl.api.ColorSwatch;
import com.example.jl.api.Product;
import com.example.jl.common.Price;
import com.example.jl.common.PriceRange;
import com.example.jl.remote.model.JLColorSwatch;
import com.example.jl.remote.model.JLPrice;
import com.example.jl.remote.model.JLProduct;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This service is responsible for rendering instances of {@link JLProduct}s into the
 * form used by the published API of this application.
 */
@Service
public class ProductPresentationService {
  private final RgbColorService rgbColorService;
  private final CurrencyService currencyService;

  @Autowired
  public ProductPresentationService(RgbColorService rgbColorService, CurrencyService currencyService) {
    this.rgbColorService = rgbColorService;
    this.currencyService = currencyService;
  }

  public Product formatForApi(JLProduct jlProduct) {
    return new Product(jlProduct.getProductId(),
                       jlProduct.getTitle(),
                       jlProduct.getColorSwatches().map(this::formatColorSwatch),
                       formatNowPrice(jlProduct.getPrice()),
                       null);
  }

  ColorSwatch formatColorSwatch(JLColorSwatch jlColorSwatch) {
    final String rgb = rgbColorService.toRgb(jlColorSwatch.getBasicColor());
    return new ColorSwatch(jlColorSwatch.getColor(), rgb, jlColorSwatch.getSkuId());
  }

  String formatNowPrice(JLPrice jlPrice) {
    Either<Price, PriceRange> price = jlPrice.getNow().getValue();
    var curr = jlPrice.getCurrency();
    var symbol = currencyService.toSymbol(curr);

    if (price.isLeft()) {
      return symbol + price.getLeft().toDisplayString();
    }
    else {
      var priceStrings = price.get().toDisplayStrings();
      var priceStringsWithCurr =  priceStrings.map2(x -> symbol + x);
      return priceStringsWithCurr._1() + " to " + priceStringsWithCurr._2();
    }
  }
}
