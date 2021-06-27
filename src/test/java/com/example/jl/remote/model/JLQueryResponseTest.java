package com.example.jl.remote.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import io.vavr.jackson.datatype.VavrModule;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

class JLQueryResponseTest {

  private final ObjectReader objectReader = new ObjectMapper().registerModule(new VavrModule()).reader().forType(JLQueryResponse.class);

  @Test void jsonToObject() {

    final JLQueryResponse rsp;

    try (InputStream in = this.getClass().getResourceAsStream("/jl-response.json")) {
      rsp = objectReader.readValue(in);
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }

    System.out.println(rsp.toString());
  }
}
