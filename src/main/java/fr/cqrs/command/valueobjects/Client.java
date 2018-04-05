package fr.cqrs.command.valueobjects;

public class Client {
  private Name clientName;

  private Client(String clientName) {
    this.clientName = Name.of(clientName);
  }

  public Name getName() {
    return clientName;
  }

  public static Client withName(String name) {
    return new Client(name);
  }
}
