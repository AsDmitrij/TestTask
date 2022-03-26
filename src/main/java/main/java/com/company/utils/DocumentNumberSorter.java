package main.java.com.company.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import main.java.com.company.models.DocumentNumberPattern;

public class DocumentNumberSorter {

    public static void sortDocumentNumbers(List<String> testData, List<DocumentNumberPattern> patternList) {
        List<SortedMap<Integer, String>> listOfMap = new ArrayList<>();
        patternList.forEach(i -> listOfMap.add(new TreeMap<>()));
        testData.forEach(documentNumber -> {
            for (int i = 0; i < patternList.size(); i++) {
                DocumentNumberPattern currentPattern = patternList.get(i);
                if (documentNumber.matches(currentPattern.getPattern())) {
                    listOfMap.get(i).put(getDocumentNumber(documentNumber, currentPattern.getKeyNumberPosition()),
                        documentNumber);
                    break;
                }
            }
        });
        printSortedMaps(listOfMap);
    }

    private static int getDocumentNumber(String documentNumber, int numberPosition) {
        return Integer.parseInt(documentNumber.split("\\-|\\/|\\s")[numberPosition]);
    }

    private static void printSortedMaps(List<SortedMap<Integer, String>> listOfMap) {
        System.out.print("\nAfter sorting:\n");
        listOfMap.forEach(map -> {
            map.values().forEach(i -> System.out.print(i + "   "));
            System.out.print("\n");
        });
    }
}
