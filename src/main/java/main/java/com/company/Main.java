package main.java.com.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import main.java.com.company.creators.CustomDocumentCreator;
import main.java.com.company.creators.TestDataForNumberParsingCreator;
import main.java.com.company.creators.TestDataPersonCreator;
import main.java.com.company.creators.TestDataRegistrationControlCard;
import main.java.com.company.creators.TestDataResolutionPersonalCard;
import main.java.com.company.models.DocumentNumberPattern;
import main.java.com.company.models.NTree.ResponsibilityTree;
import main.java.com.company.models.PersonCard;
import main.java.com.company.models.PersonalResolutionCard;
import main.java.com.company.models.RegistrationControlCard;
import main.java.com.company.models.Resolution;
import main.java.com.company.utils.DocumentNumberSorter;
import main.java.com.company.utils.ResolutionConverter;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.Document;

public class Main {

    public static void main(String[] args) throws Docx4JException {
        /*
        Задание №1
        Список паттернов взят с предложенных тестовых примеров, и задан константами в TestDataForNumberParsingCreator
        Вывод результата производится в консоль
         */
        List<DocumentNumberPattern> patternList = TestDataForNumberParsingCreator.createPatternList();
        List<String> testDataForParseAndSort = TestDataForNumberParsingCreator.createTestData(500, 100, patternList);
        System.out.print("Test data before sorting:\n");
        testDataForParseAndSort.forEach(i -> System.out.print(i + "   "));
        DocumentNumberSorter.sortDocumentNumbers(testDataForParseAndSort, patternList);
        System.out.print("\nChanges according comments:\n");//новый метод исходя из замечаний
        System.out.print("Test data before sorting:\n");
        List<String> testDataForParseAndSortWithNotExistingPatterns = TestDataForNumberParsingCreator.createTestDataWithNotExistingPatterns(500, 100, patternList);
        testDataForParseAndSortWithNotExistingPatterns.forEach(i -> System.out.print(i + "   "));
        DocumentNumberSorter.sortDocumentNumbersWithoutDistributionByPattern(testDataForParseAndSortWithNotExistingPatterns, patternList);
        /*
        Задание №2
        Текущая реализация сделана с учетом случайного расположения хранения карточек
        и исходя из предложенных тестовых данных - инициатором может являться лишь один человек
         */
        RegistrationControlCard registrationControlCard =
            TestDataRegistrationControlCard.createRegistrationControlCard();//создание тестовых данных для первой таблице в предложенном документе
        List<PersonCard> personCards = TestDataPersonCreator.createPersonCards(10);//создание 10 карточек в которых первое поле - уникальный идентификатор,
        // второе - объект хранящий информацию о нём (в дальнейшем будет необходимо лишь имя, фамилия, отчество)
        List<PersonalResolutionCard> personalResolutionCard =
            TestDataResolutionPersonalCard.createResolutionPersonalCard(personCards);
        //создание карточек резолюций, объект состоит из полей аналогичным второму типу таблиц
        //из предложенного шаблона для вывода (за исключением имен автора и исполнителя, тут они заменены на id, согласно карточкам созданным выше)
        // и с добавлением идентификатора самой карточки резолюции

        List<Resolution> resolutionList = new ArrayList<>();
        for (PersonalResolutionCard resolutionCard : personalResolutionCard) {
            resolutionList.add(new Resolution(resolutionCard.getAuthorId(), resolutionCard.getExecutorId()));
        }
        //получение из списка карточек резолюций лишь id автора и исполнителя
        ResponsibilityTree responsibilityTree =
            ResolutionConverter.convertResolutionListToResponsibilitiesTree(resolutionList);
        new ResponsibilityTree().makeNodeIndexes(responsibilityTree.root);
        for (PersonalResolutionCard resolutionCard : personalResolutionCard) {
            int executorId = resolutionCard.getExecutorId();
            ResponsibilityTree findTree = new ResponsibilityTree();
            findTree.findNode(responsibilityTree.root, executorId);
            List<Integer> path = findTree.valueAddress;
            path.remove(path.get(0));
            resolutionCard.setRegistrationCardIndex(path.stream().map(String::valueOf)
                                                        .collect(Collectors.joining(".")));
        }

        CustomDocumentCreator documentCreator = new CustomDocumentCreator();
        WordprocessingMLPackage mainWordprocessingMLPackage = documentCreator.createMainDocumentPartWithParams();
        Document documentBody = ResolutionRegistrationDocumentTemplate.createResolutionRegistrationDocumentBody(mainWordprocessingMLPackage,
            registrationControlCard, personalResolutionCard.stream()
                                                           .sorted(Comparator.comparing(
                                                               PersonalResolutionCard::getRegistrationCardIndex))
                                                           .collect(Collectors.toList()), personCards);
        documentCreator.saveDocument(mainWordprocessingMLPackage, documentBody);
    }
}



