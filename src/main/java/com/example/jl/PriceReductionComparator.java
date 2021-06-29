package com.example.jl;

import com.example.jl.remote.model.JLProduct;
import io.vavr.collection.List;
import io.vavr.control.Either;

import java.util.Comparator;

public class PriceReductionComparator implements Comparator<JLProduct> {

  @Override public int compare(JLProduct left, JLProduct right) {
    // To compare 2 products by their reductions, they must have actually been reduced.

    var leftNow = left.getPrice().getNow();
    var leftWas = left.getPrice().getWas();
    var rightNow = right.getPrice().getNow();
    var rightWas = right.getPrice().getWas();

    // This shouldn't happen as all products should have prices, but just in case...
    if (leftNow == null || rightNow == null) {
      return 0;
    }

    if (leftWas == null) {
      // If the left product has no 'was' price, we'll sort it low.
      return -1;
    }
    else if (rightWas == null) {
      return 1;
    }

    var leftNowValue = leftNow.getValue();
    var leftWasValue = leftWas.getValue();
    var rightNowValue = rightNow.getValue();
    var rightWasValue = rightWas.getValue();

    var containsPriceRange = List.of(leftNowValue, leftWasValue, rightNowValue, rightWasValue)
                                 .exists(Either::isRight);
    if (containsPriceRange) {
      // If any product has a price range, rather than a price, then the reduction is unclear
      // and we shall sort it low.
      return -1;
    }
    else {
      var leftReduction = leftWasValue.getLeft().difference(leftNowValue.getLeft());
      var rightReduction = rightWasValue.getLeft().difference(rightNowValue.getLeft());
      return leftReduction.compareTo(rightReduction);
    }
  }
}
