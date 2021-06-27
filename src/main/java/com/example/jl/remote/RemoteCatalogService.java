package com.example.jl.remote;

import com.example.jl.remote.model.JLQueryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import io.vavr.jackson.datatype.VavrModule;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class RemoteCatalogService {

  // TODO Make this class make a remote call.
  private final ObjectReader objectReader = new ObjectMapper().registerModule(new VavrModule()).reader().forType(JLQueryResponse.class);

  public JLQueryResponse query() {

    final JLQueryResponse rsp;

    try (InputStream in = this.getClass().getResourceAsStream("/jl-response.json")) {
      rsp = objectReader.readValue(in);
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }

    return rsp;
  }
}
