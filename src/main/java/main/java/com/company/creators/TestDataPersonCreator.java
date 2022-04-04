package main.java.com.company.creators;

import com.devskiller.jfairy.Fairy;
import java.util.ArrayList;
import java.util.List;
import main.java.com.company.models.PersonCard;

public class TestDataPersonCreator {
    public static List<PersonCard> createPersonCards(int numberOfPersons) {
        List<PersonCard> personCards = new ArrayList<>();
        for (int i = 0; i < numberOfPersons; i++) {
            personCards.add(new PersonCard(Fairy.create().person(), i + 1));
        }
        return personCards;
    }
}
