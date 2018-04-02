package fr.cqrs.domain.valueobjects;

public class Client {
  private Name clientName;

  public Client(String clientName) {
    this.clientName = new Name(clientName);
  }

  public Name getName() {
    return clientName;
  }
}
