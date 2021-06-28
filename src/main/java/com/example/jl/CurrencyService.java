package com.example.jl;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyService {
  private final Map<String, String> nameToSymbol = Map.of("GBP", "£", "USD", "$", "EUR", "€");

  public String toSymbol(String name) {
    return nameToSymbol.getOrDefault(name, "");
  }
}
