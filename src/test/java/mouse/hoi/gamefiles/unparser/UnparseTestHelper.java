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
        Map<Object, Integer> frequencyMap1 = buildFrequencyMap(list1);
        Map<Object, Integer> frequencyMap2 = buildFrequencyMap(list2);

        List<Object> uniqueInList1 = new ArrayList<>();
        List<Object> uniqueInList2 = new ArrayList<>();

        for (Object element : frequencyMap1.keySet()) {
            int countInList1 = frequencyMap1.get(element);
            int countInList2 = frequencyMap2.getOrDefault(element, 0);
            if (countInList1 > countInList2) {
                uniqueInList1.add(element);
            }
            if (countInList1 < countInList2) {
                uniqueInList2.add(element);
            }
        }

        for (Object element : frequencyMap2.keySet()) {
            if (!frequencyMap1.containsKey(element)) {
                uniqueInList2.add(element);
            }
        }
        assertTrue(uniqueInList1.isEmpty() && uniqueInList2.isEmpty(),
                "\nList 1 unique elements: " + uniqueInList1 + "\nList 2 unique elements: " + uniqueInList2);
    }

    private Map<Object, Integer> buildFrequencyMap(List<?> list) {
        Map<Object, Integer> frequencyMap = new HashMap<>();
        for (Object element : list) {
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
