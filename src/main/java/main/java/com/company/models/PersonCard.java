package main.java.com.company.models;

import com.devskiller.jfairy.producer.person.Person;

public class PersonCard {
    private final Person person;
    private final int id;

    public PersonCard(Person person, int id) {
        this.person = person;
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public int getId() {
        return id;
    }
}
