package com.example.jl;

import com.example.jl.common.Price;
import com.example.jl.common.PriceRange;
import com.example.jl.remote.model.JLProduct;
import io.vavr.control.Either;

import java.util.function.Predicate;

public class ReducedProductFilter implements Predicate<JLProduct> {

  @Override public boolean test(JLProduct product) {
    var now = product.getPrice().getNow();
    var was = product.getPrice().getWas();

    // No 'was' price means the product has not been reduced in price.
    // We check for no 'now' price just in case.
    if (now == null || was == null) {
      return false;
    }

    Either<Price, PriceRange> nowValue = now.getValue();
    Either<Price, PriceRange> wasValue = was.getValue();

    var bothArePrices = nowValue.isLeft() && wasValue.isLeft();
    var bothArePriceRanges = nowValue.isRight() && wasValue.isRight();

    if (bothArePrices) {
      return nowValue.getLeft().isLessThan(wasValue.getLeft());
    }
    else if (bothArePriceRanges) {
      return nowValue.get().isLessThan(wasValue.get());
    }
    else {
      return false;
    }
  }
}
