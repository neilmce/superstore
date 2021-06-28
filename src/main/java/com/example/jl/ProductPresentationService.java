package com.example.jl;

import com.example.jl.PriceReductionService.LabelType;
import com.example.jl.api.ColorSwatch;
import com.example.jl.api.Product;
import com.example.jl.common.Price;
import com.example.jl.common.PriceRange;
import com.example.jl.remote.model.JLColorSwatch;
import com.example.jl.remote.model.JLPrice;
import com.example.jl.remote.model.JLProduct;
import io.vavr.control.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

/**
 * This service is responsible for rendering instances of {@link JLProduct}s into the
 * form used by the published API of this application.
 */
@Service
public class ProductPresentationService {
  private final static Logger LOGGER = LoggerFactory.getLogger(ProductPresentationService.class);

  private final RgbColorService rgbColorService;
  private final CurrencyService currencyService;

  @Autowired
  public ProductPresentationService(RgbColorService rgbColorService, CurrencyService currencyService) {
    this.rgbColorService = rgbColorService;
    this.currencyService = currencyService;
  }

  public Product formatForApi(JLProduct jlProduct, LabelType labelType) {
    JLPrice price = jlProduct.getPrice();
    return new Product(jlProduct.getProductId(),
                       jlProduct.getTitle(),
                       jlProduct.getColorSwatches().map(this::formatColorSwatch).asJava(),
                       formatPrice(price.getCurrency(), price.getNow().getValue()),
                       formatPriceLabel(labelType, price));
  }

  ColorSwatch formatColorSwatch(JLColorSwatch jlColorSwatch) {
    final String rgb = rgbColorService.toRgb(jlColorSwatch.getBasicColor());
    return new ColorSwatch(jlColorSwatch.getColor(), rgb, jlColorSwatch.getSkuId());
  }

  String formatPrice(String currency, Either<Price, PriceRange> price) {
    var symbol = currencyService.toSymbol(currency);

    if (price.isLeft()) {
      return symbol + price.getLeft().toDisplayString();
    }
    else {
      var priceStrings = price.get().toDisplayStrings();
      return format("%s%s to %s%s", symbol, priceStrings._1(), symbol, priceStrings._2());
    }
  }

  String formatPriceLabel(LabelType labelType, JLPrice price) {
    String result = switch (labelType) {
      case SHOW_WAS_NOW -> formatWasNow(price);
      case SHOW_WAS_THEN_NOW -> formatWasThenNow(price);
      case SHOW_PERC_DISCOUNT -> formatPercentDiscount(price);
    };
    LOGGER.info("price label: {}", result);
    return result;
  }

  String formatWasNow(JLPrice price) {
    var was = price.getWas();
    var now = price.getNow();

    var wasPrice = was == null ? "-" : formatPrice(price.getCurrency(), was.getValue());
    var nowPrice = now == null ? "-" : formatPrice(price.getCurrency(), now.getValue());

    return format("Was %s, now %s", wasPrice == null ? "-" : wasPrice, nowPrice);
  }

  String formatWasThenNow(JLPrice price) {
    var was = price.getWas();
    var then1 = price.getThen1();
    var then2 = price.getThen2();
    var now = price.getNow();

    var then = then2 == null ? then1 : then2;

    if (then == null) {
      return formatWasNow(price);
    }

    var wasPrice = was == null ? "-" : formatPrice(price.getCurrency(), was.getValue());
    var thenPrice = formatPrice(price.getCurrency(), then.getValue());
    var nowPrice = now == null ? "-" : formatPrice(price.getCurrency(), now.getValue());

    return format("Was %s, then %s, now %s", wasPrice == null ? "-" : wasPrice, thenPrice, nowPrice);
  }

  String formatPercentDiscount(JLPrice price) {
    var was = price.getWas();
    var now = price.getNow();

    boolean wasAndNowBothPrices = was != null && now != null && was.getValue().isLeft() && now.getValue().isLeft();
    if (wasAndNowBothPrices) {
      Price wasPrice = price.getWas().getValue().getLeft();
      Price nowPrice = price.getNow().getValue().getLeft();

      if (wasPrice.isGreaterThan(nowPrice)) {
        float wasF = wasPrice.getMajor() + (wasPrice.getMinor() / 100F);
        float nowF = nowPrice.getMajor() + (nowPrice.getMinor() / 100F);
        var percentageReduction = (100 * (wasF - nowF) / wasF);

        return format("%.0f%% off - now %s", percentageReduction, formatPrice(price.getCurrency(), was.getValue()));
      }
    }
    return formatWasNow(price);
  }
}
