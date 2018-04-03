package fr.cqrs.utils;

import java.util.UUID;

public class UUIDGenerator implements IdGenerator {
  public String generate() {
    return UUID.randomUUID().toString();
  }
}
