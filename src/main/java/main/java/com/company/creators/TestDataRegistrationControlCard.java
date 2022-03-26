package main.java.com.company.creators;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.person.Person;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import main.java.com.company.models.DocumentNumberPattern;
import main.java.com.company.models.RegistrationControlCard;

public class TestDataRegistrationControlCard {
    private final static String RECEIVE_INDEX_NUMBER_PATTERN = "[0-9]{2}[-]{1}[0-9]{2}/{1}[0-9]{2}";
    private final static String DOCUMENT_INDEX_NUMBER_PATTERN = "[0-9]{1,2}[-]{1}[0-9]{1,2}";
    private final static String OUTGOING_INDEX_NUMBER_PATTERN = "[0-9]{1,2}[-]{1}[0-9]{1}";

    public static RegistrationControlCard createRegistrationControlCard() {
        Random rand = new Random();
        Person firstPerson = Fairy.create().person();
        Person secondPerson = Fairy.create().person();
        LocalDateTime date = LocalDateTime.now().plusDays(rand.nextInt(100));
        List<String> receiveIndexes = TestDataForNumberParsingCreator.createTestData(2, 1,
            new ArrayList<>(Arrays.asList(new DocumentNumberPattern(RECEIVE_INDEX_NUMBER_PATTERN, 0))));
        List<String> documentIndexes = TestDataForNumberParsingCreator.createTestData(2, 1,
            new ArrayList<>(Arrays.asList(new DocumentNumberPattern(DOCUMENT_INDEX_NUMBER_PATTERN, 0))));
        List<String> outgoingIndex = TestDataForNumberParsingCreator.createTestData(2, 1,
            new ArrayList<>(Arrays.asList(new DocumentNumberPattern(OUTGOING_INDEX_NUMBER_PATTERN, 0))));
        return new RegistrationControlCard(date, receiveIndexes.get(0), firstPerson.getFullName(),
            documentIndexes.get(0), outgoingIndex.get(0),
            date, secondPerson.getFullName(), Fairy.create().textProducer().randomString(50),
            Fairy.create().textProducer().randomString(10), Fairy.create().textProducer().randomString(100));
    }
}
