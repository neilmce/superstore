package com.example.jl.remote.serdes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public abstract class ValueOrFromToDeserializer<T> extends StdDeserializer<T> {

  public ValueOrFromToDeserializer() {
    this(null);
  }

  public ValueOrFromToDeserializer(Class<T> vc) {
    super(vc);
  }

  @Override
  public T deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
    JsonNode node = jp.getCodec().readTree(jp);
    if (node.isContainerNode()) {
      return fromTo(node.get("from").asText(), node.get("to").asText());
    }
    else {
      return value(node.asText());
    }
  }

  protected abstract T fromTo(String from, String to);
  protected abstract T value(String value);
}