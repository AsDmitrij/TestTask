package main.java.com.company.creators;

import com.devskiller.jfairy.Fairy;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import main.java.com.company.models.PersonCard;
import main.java.com.company.models.PersonalResolutionCard;

public class TestDataResolutionPersonalCard {

    /*
     * 1) Создается копия списка сгенерированных карточек работников,
     * теперь nonUsedPersonCards будут храниться работники, которым ещё ничего не поручали, а в usedPersonCards те, которым уже что-то было поручено.
     * Это необходимо сделать, чтобы не было циклов в графе и он оставался деревом.
     * 2) Из предложенного списка карточек работников выбирается первый
     * 3) Из списка Случайным образом определяется от 1 до 3 */
    public static List<PersonalResolutionCard> createResolutionPersonalCard(List<PersonCard> personCards) {
        List<PersonalResolutionCard> personalResolutionCards = new ArrayList<>();
        List<PersonCard> nonUsedPersonCards = new ArrayList<PersonCard>(personCards);
        List<PersonCard> usedPersonCards = new ArrayList<PersonCard>(personCards);
        /* 1) Создается копия списка сгенерированных карточек работников,
         * теперь nonUsedPersonCards будут храниться работники, которым ещё ничего не поручали, а в usedPersonCards те, которым уже что-то было поручено.
         * Это необходимо сделать, чтобы не было циклов в графе и он оставался деревом.*/
        while (usedPersonCards.size() > 0) { //цикл идет пока все не будет поставлено соответствие Автор-Исполнитель, т.е. чтобы все пришедшее количество карточек было задействовано
            int numberOfExecutors = new Random().nextInt(3) + 1;//случайным образом определяется количество исполнителей
            PersonCard authorCard = usedPersonCards.get(0);//берем первого из списка тех кто может быть автором резолюции
            usedPersonCards.remove(authorCard);//удаляем его из него
            nonUsedPersonCards.remove(authorCard);//так же удаляем его из списка тех, кто может быть исполнителем
            for (int j = 0; j < numberOfExecutors; j++) {
                if (nonUsedPersonCards.size() == 0) {//проверяем на то, остались ли ещё те кто может быть исполнителем, в противном случае переходим к новому автору
                    break;
                }
                PersonCard executorCard = nonUsedPersonCards.get(0);//берем первого из списка исполнителей
                LocalDateTime now = LocalDateTime.now();
                PersonalResolutionCard personalResolutionCard =
                    new PersonalResolutionCard(now, authorCard.getId(), executorCard.getId(),
                        Fairy.create().textProducer().randomString(10), now,
                        Fairy.create().textProducer().randomString(10));//создает карточку со всеми данными
                personalResolutionCards.add(personalResolutionCard);//добавляем её
                nonUsedPersonCards.remove(executorCard);// и убираем из списка тех кто может быть исполнителем
            }
        }
        return personalResolutionCards;//возвращаем список который условно связан при помощи authorId и executorId
    }
}
