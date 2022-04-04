package main.java.com.company.creators;

import com.mifmif.common.regex.Generex;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import main.java.com.company.models.DocumentNumberPattern;
import org.apache.commons.lang.RandomStringUtils;

public class TestDataForNumberParsingCreator {
    private static final String FIRST_PATTERN = "[0-9]{1}";
    private static final String SECOND_PATTERN = "[0-9]{2}[-]{1}[0-9]{2}/{1}[0-9]{2}";
    private static final String THIRD_PATTERN = "[0-9]{1,2}[-]{1}[А-Я]{1}";
    private static final String FOURTH_PATTERN = "[А-Я]{1}[-]{1}[0-9]{1,2}";
    private static final String FIFTH_PATTERN = "[0-9]{2,3}/{1}[А-Я]{1,2}";

    public static List<String> createTestData(int maxSizeListTestData, int minSizeListTestData,
                                              List<DocumentNumberPattern> patternList) {
        int sizeListTestData =
            new Random().nextInt(maxSizeListTestData - minSizeListTestData + 1) + minSizeListTestData;
        List<String> testData = new ArrayList<>();
        for (int i = 0; i < sizeListTestData; i++) {
            testData.add(new Generex(patternList.get(new Random().nextInt(patternList.size())).getPattern()).random());
        }
        return testData;
    }

    public static List<String> createTestDataWithNotExistingPatterns(int maxSizeListTestData, int minSizeListTestData,
                                                                     List<DocumentNumberPattern> patternList) {
        int sizeListTestData =
            new Random().nextInt(maxSizeListTestData - minSizeListTestData + 1) + minSizeListTestData;
        List<String> testData = new ArrayList<>();
        for (int i = 0; i < sizeListTestData; i++) {
            testData.add(new Generex(patternList.get(new Random().nextInt(patternList.size())).getPattern()).random());
        }

        for (int i = 0; i < sizeListTestData / 3; i++) {
            int numberSignKeyNumber = new Random().nextInt(3) + 1;
            int numberSignFirstPart = new Random().nextInt(4) + 1;
            int numberSignSecondPart = new Random().nextInt(4) + 1;
            testData.add(
                RandomStringUtils.randomAlphanumeric(numberSignFirstPart) + "/" + RandomStringUtils.randomNumeric(
                    numberSignKeyNumber) + "-" + RandomStringUtils.randomAlphanumeric(numberSignSecondPart));
        }

        return testData;
    }

    public static List<DocumentNumberPattern> createPatternList() {
        return new ArrayList<>(Arrays.asList(new DocumentNumberPattern(FIRST_PATTERN, 0),
            new DocumentNumberPattern(SECOND_PATTERN, 2),
            new DocumentNumberPattern(THIRD_PATTERN, 0),
            new DocumentNumberPattern(FOURTH_PATTERN, 1),
            new DocumentNumberPattern(FIFTH_PATTERN, 0)));
    }
}
