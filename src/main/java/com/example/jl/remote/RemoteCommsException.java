package com.example.jl.remote;

/**
 * This class represents a communications error between this application and
 * the remote catalog service.
 */
public class RemoteCommsException extends RuntimeException {
  public RemoteCommsException(String message, Throwable cause) {
    super(message, cause);
  }

  public RemoteCommsException(String message) {
    super(message);
  }
}
