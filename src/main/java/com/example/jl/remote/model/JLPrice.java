package com.example.jl.remote.model;

import com.example.jl.common.PriceRange;
import com.example.jl.remote.serdes.FromToDeserializers.NowDeserializer;
import com.example.jl.remote.serdes.FromToDeserializers.Then1Deserializer;
import com.example.jl.remote.serdes.FromToDeserializers.Then2Deserializer;
import com.example.jl.remote.serdes.FromToDeserializers.WasDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.vavr.control.Either;

public class JLPrice {
  private final Was was;
  private final Then1 then1;
  private final Then2 then2;
  private final Now now;
  private final String uom;
  private final String currency;

  public JLPrice(@JsonProperty("was")      Was was,
                 @JsonProperty("then1")    Then1 then1,
                 @JsonProperty("then2")    Then2 then2,
                 @JsonProperty("now")      Now now,
                 @JsonProperty("uom")      String uom,
                 @JsonProperty("currency") String currency) {
    this.was = was;
    this.then1 = then1;
    this.then2 = then2;
    this.now = now;
    this.uom = uom;
    this.currency = currency;
  }

  public Was getWas() {
    return was;
  }

  public Then1 getThen1() {
    return then1;
  }

  public Then2 getThen2() {
    return then2;
  }

  public Now getNow() {
    return now;
  }

  public String getUom() {
    return uom;
  }

  public String getCurrency() {
    return currency;
  }

  public static class PriceRangeOrValue {
    private final Either<PriceRange, String> value;

    protected PriceRangeOrValue(Either<PriceRange, String> value) {
      this.value = value;
    }

    public Either<PriceRange, String> getValue() {
      return value;
    }
  }

  @JsonDeserialize(using = NowDeserializer.class)
  public static class Now extends PriceRangeOrValue {
    public Now(String value) {
      super(Either.right(value));
    }

    public Now(String from, String to) {
      super(Either.left(PriceRange.fromTo(from, to)));
    }
  }

  @JsonDeserialize(using = WasDeserializer.class)
  public static class Was extends PriceRangeOrValue {
    public Was(String value) {
      super(Either.right(value));
    }

    public Was(String from, String to) {
      super(Either.left(PriceRange.fromTo(from, to)));
    }
  }

  @JsonDeserialize(using = Then1Deserializer.class)
  public static class Then1 extends PriceRangeOrValue {
    public Then1(String value) {
      super(Either.right(value));
    }

    public Then1(String from, String to) {
      super(Either.left(PriceRange.fromTo(from, to)));
    }
  }

  @JsonDeserialize(using = Then2Deserializer.class)
  public static class Then2 extends PriceRangeOrValue {
    public Then2(String value) {
      super(Either.right(value));
    }

    public Then2(String from, String to) {
      super(Either.left(PriceRange.fromTo(from, to)));
    }
  }
}
