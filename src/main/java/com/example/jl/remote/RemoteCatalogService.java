package com.example.jl.remote;

import com.example.jl.remote.model.JLQueryResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import io.vavr.jackson.datatype.VavrModule;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

/** This service makes HTTP GET calls to the remote product catalog. */
@Service
public class RemoteCatalogService {
  private static final String REMOTE_URL = "https://api.johnlewis.com/search/api/rest/v2/catalog/products/search/keyword?q=dresses&key=AIzaSyDD_6O5gUgC4tRW5f9kxC0_76XRC8W7_mI";

  private final ObjectReader objectReader = new ObjectMapper()
      .registerModule(new VavrModule())
      .reader()
      .forType(JLQueryResponse.class);

  public JLQueryResponse query() {
    var httpRequest = HttpRequest.newBuilder()
                                   .GET()
                                   .uri(URI.create(REMOTE_URL))
                                   .timeout(Duration.ofMinutes(1))
                                   .header("Content-Type", "application/json")
                                 .build();
    final HttpResponse<String> httpRsp;
    try {
      httpRsp = HttpClient.newHttpClient().send(httpRequest, BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new RemoteCommsException("Remote call failed.", e);
    }

    if (httpRsp.statusCode() != 200) {
      throw new RemoteCommsException("Remote call failed. Status code: " + httpRsp.statusCode());
    }

    JLQueryResponse result = null;
    try {
      result = objectReader.readValue(httpRsp.body());
    } catch (JsonProcessingException e) {
      throw new RemoteCommsException("Failed to process response from remote server.", e);
    }

    return result;
  }
}
