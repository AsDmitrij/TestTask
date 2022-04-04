package main.java.com.company.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Objects;
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

    public static void sortDocumentNumbersWithoutDistributionByPattern(List<String> testData,
                                                                       List<DocumentNumberPattern> patternList) {

        List<String> documentNumbersWithOutPatterns = new ArrayList<>();//можно в последствие выносить данный список в лог, чтобы добавлять новые паттерны
        List<SortedMap<Integer, String>> listOfMap = new ArrayList<>();
        listOfMap.add(new TreeMap<>());

        HashMap<String, Integer> map = new HashMap<String, Integer>();


        for (String documentNumber : testData) {
            boolean isPatternForDocumentExists = false;
            for (DocumentNumberPattern currentPattern : patternList) {
                if (documentNumber.matches(currentPattern.getPattern())) {
                    isPatternForDocumentExists = true;
                    listOfMap.get(0).put(getDocumentNumber(documentNumber, currentPattern.getKeyNumberPosition()),
                        documentNumber);
                    map.put(documentNumber, getDocumentNumber(documentNumber, currentPattern.getKeyNumberPosition()));
                    break;
                }
            }
            if(!isPatternForDocumentExists){
                documentNumbersWithOutPatterns.add(documentNumber);
                listOfMap.get(0).put(getDocumentNumberWithoutPattern(documentNumber), documentNumber);
                map.put(documentNumber, getDocumentNumberWithoutPattern(documentNumber));
            }
        }
        printHashMap(map);
    }

    private static int getDocumentNumber(String documentNumber, int numberPosition) {
        return Integer.parseInt(documentNumber.split("\\-|\\/|\\s")[numberPosition]);
    }

    private static int getDocumentNumberWithoutPattern(String documentNumber) {
        String numberWithoutLetters = deleteLetters(documentNumber);
        String[] splitString = numberWithoutLetters.split("\\-|\\/|\\s");
        for (String s : splitString) {
            if (!Objects.equals(s, ""))
                return Integer.parseInt(s);
        }
        return 0;
    }

    private static String deleteLetters (String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i)))
                sb.append(s.charAt(i));
            if (!Character.isLetterOrDigit(s.charAt(i)))
                sb.append(s.charAt(i));
        }
        return sb.toString();
    }

    private static void printSortedMaps(List<SortedMap<Integer, String>> listOfMap) {
        System.out.print("\nAfter sorting:\n");
        listOfMap.forEach(map -> {
            map.values().forEach(i -> System.out.print(i + "   "));
            System.out.print("\n");
        });
    }

    private static void printHashMap(HashMap<String, Integer> map) {
        System.out.print("\nAfter sorting:\n");
        map.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue()).forEach(i -> System.out.print(i.getKey() + "   "));
    }
}
