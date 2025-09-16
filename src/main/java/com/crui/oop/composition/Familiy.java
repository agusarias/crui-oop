package com.crui.oop.composition;

import java.util.ArrayList;
import java.util.List;

import com.crui.oop.encapsulation.Person;
import com.crui.oop.shared.Picture;

// Lawyer contains a Person
class LawyerUsingComposition {
  private Person person;
  public String lawFirm;

  public LawyerUsingComposition(Person person, String lawFirm) {
    this.person = person;
  }

  public String getName() {
    return this.person.getName();
  }
}

public class Familiy implements com.crui.oop.shared.Fotografiable {
  private List<Person> members;

  public Familiy() {
    this.members = new ArrayList<>(); // []
  }

  public Familiy(List<Person> members) {
    this.members = members;
  }

  public void addMember(Person person) {
    this.members.add(person);
  }

  public List<Person> getMembers() {
    return this.members;
  }

  public List<Person> getAdults() {
    List<Person> adults = new ArrayList<>();
    for (Person person : members) {
      if (person.getAge() > 18) {
        adults.add(person);
      }
    }
    return adults;
  }

  @Override
  public Picture takePicture() {
    return null;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Family: {");
    for (Person member : members) {
      sb.append(member.toString()).append(", ");
    }
    if (!members.isEmpty()) {
      sb.setLength(sb.length() - 2);
    }
    sb.append("}");
    return sb.toString();
  }
}
