package com.example.jl.remote.serdes;

import com.example.jl.remote.model.JLPrice.Now;
import com.example.jl.remote.model.JLPrice.Then1;
import com.example.jl.remote.model.JLPrice.Then2;
import com.example.jl.remote.model.JLPrice.Was;

public class FromToDeserializers {
  public static class NowDeserializer extends ValueOrFromToDeserializer<Now> {

    public NowDeserializer() {
      this(null);
    }

    public NowDeserializer(Class<Now> vc) {
      super(vc);
    }

    protected Now fromTo(String from, String to) {
      return new Now(from, to);
    }
    protected Now value(String value) {
      return new Now(value);
    }
  }

  public static class WasDeserializer extends ValueOrFromToDeserializer<Was> {

    public WasDeserializer() {
      this(null);
    }

    public WasDeserializer(Class<Was> vc) {
      super(vc);
    }

    protected Was fromTo(String from, String to) {
      return new Was(from, to);
    }
    protected Was value(String value) {
      return new Was(value);
    }
  }

  public static class Then1Deserializer extends ValueOrFromToDeserializer<Then1> {

    public Then1Deserializer() {
      this(null);
    }

    public Then1Deserializer(Class<Then1> vc) {
      super(vc);
    }

    protected Then1 fromTo(String from, String to) {
      return new Then1(from, to);
    }
    protected Then1 value(String value) {
      return new Then1(value);
    }
  }

  public static class Then2Deserializer extends ValueOrFromToDeserializer<Then2> {

    public Then2Deserializer() {
      this(null);
    }

    public Then2Deserializer(Class<Then2> vc) {
      super(vc);
    }

    protected Then2 fromTo(String from, String to) {
      return new Then2(from, to);
    }
    protected Then2 value(String value) {
      return new Then2(value);
    }
  }
}
