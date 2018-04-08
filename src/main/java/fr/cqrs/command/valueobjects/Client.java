package fr.cqrs.command.valueobjects;

public class Client {
  private Name clientName;

  private Client(Name clientName) {
    this.clientName = clientName;
  }

  public Name getName() {
    return clientName;
  }

  public static Client withName(String name) {
    return new Client(Name.of(name));
  }
}
