package mouse.hoi.gamefiles.unparser;

import mouse.hoi.gamefiles.parser.parse.FileFormatter;
import mouse.hoi.gamefiles.parser.parse.SimpleFileFormatter;
import mouse.hoi.gamefiles.parser.parse.TokenCollection;
import mouse.hoi.lib.filemanager.FileManager;
import mouse.hoi.lib.filemanager.FileManagerImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnparseTestHelper {
    public void compareTokens(TokenCollection origin, TokenCollection after) {
        List<String> originTokens = origin.getAll();
        List<String> allTokens = after.getAll();
        compareLists(originTokens, allTokens);
    }

    public void compareLists(List<String> list1, List<String> list2) {
        Map<String, Integer> frequencyMap1 = buildFrequencyMap(list1);
        Map<String, Integer> frequencyMap2 = buildFrequencyMap(list2);

        List<String> uniqueInList1 = new ArrayList<>();
        List<String> uniqueInList2 = new ArrayList<>();

        for (String element : frequencyMap1.keySet()) {
            int countInList1 = frequencyMap1.get(element);
            int countInList2 = frequencyMap2.getOrDefault(element, 0);
            if (countInList1 > countInList2) {
                uniqueInList1.add(element);
            }
            if (countInList1 < countInList2) {
                uniqueInList2.add(element);
            }
        }

        for (String element : frequencyMap2.keySet()) {
            if (!frequencyMap1.containsKey(element)) {
                uniqueInList2.add(element);
            }
        }

        eliminateNumerics(uniqueInList1, uniqueInList2);
        assertTrue(uniqueInList1.isEmpty() && uniqueInList2.isEmpty(),
                "\nList 1 unique elements: " + uniqueInList1 + "\nList 2 unique elements: " + uniqueInList2);
    }

    private void eliminateNumerics(List<String> uniqueInList1, List<String> uniqueInList2) {
        List<String> list1Numerics = new ArrayList<>();
        List<String> list2Numerics = new ArrayList<>();
        for (String s : uniqueInList1) {
            if (isNumeric(s)) {
                list1Numerics.add(s);
            }
        }
        for (String s : uniqueInList2) {
            if (isNumeric(s)) {
                list2Numerics.add(s);
            }
        }
        List<String> commonNumerics1 = new ArrayList<>();
        List<String> commonNumerics2 = new ArrayList<>();
        for (String s : list1Numerics) {
            Double d = Double.parseDouble(s);
            for (String s2 : list2Numerics) {
                Double d2 = Double.parseDouble(s2);
                if (d.equals(d2)) {
                    commonNumerics1.add(s);
                    commonNumerics2.add(s2);
                }
            }
        }
        uniqueInList1.removeAll(commonNumerics1);
        uniqueInList2.removeAll(commonNumerics2);
    }
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Map<String, Integer> buildFrequencyMap(List<String> list) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String element : list) {
            frequencyMap.put(element, frequencyMap.getOrDefault(element, 0) + 1);
        }
        return frequencyMap;
    }

    public TokenCollection tokenize(String file) {
        FileManager fileManager = new FileManagerImpl();
        String content = fileManager.read(file);
        return tokenizeContent(content);
    }

    public TokenCollection tokenizeContent(String content) {
        FileFormatter fileFormatter = new SimpleFileFormatter();
        return new TokenCollection(fileFormatter.formatAndTokenize(content));
    }
}
