package com.example.jl.remote;

public class RemoteCommsException extends RuntimeException {
  public RemoteCommsException(String message, Throwable cause) {
    super(message, cause);
  }

  public RemoteCommsException(String message) {
    super(message);
  }
}
