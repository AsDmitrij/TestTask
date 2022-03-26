package main.java.com.company.creators;

import com.devskiller.jfairy.Fairy;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import main.java.com.company.models.PersonCard;
import main.java.com.company.models.PersonalResolutionCard;

public class TestDataResolutionPersonalCard {

    public static List<PersonalResolutionCard> createResolutionPersonalCard(List<PersonCard> personCards) {
        List<PersonalResolutionCard> personalResolutionCards = new ArrayList<>();
        List<PersonCard> nonUsedPersonCards = new ArrayList<PersonCard>(personCards);
        List<PersonCard> usedPersonCards = new ArrayList<PersonCard>(personCards);
        while (usedPersonCards.size() > 0) {
            int numberOfExecutors = new Random().nextInt(3) + 1;
            PersonCard authorCard = usedPersonCards.get(0);
            usedPersonCards.remove(authorCard);
            nonUsedPersonCards.remove(authorCard);
            for (int j = 0; j < numberOfExecutors; j++) {
                if (nonUsedPersonCards.size() <= j) {
                    break;
                }
                PersonCard executorCard = nonUsedPersonCards.get(j);
                LocalDateTime now = LocalDateTime.now();
                PersonalResolutionCard personalResolutionCard =
                    new PersonalResolutionCard(now, authorCard.getId(), executorCard.getId(),
                        Fairy.create().textProducer().randomString(10), now,
                        Fairy.create().textProducer().randomString(10));
                personalResolutionCards.add(personalResolutionCard);
                nonUsedPersonCards.remove(executorCard);
            }
        }
        return personalResolutionCards;
    }
}
