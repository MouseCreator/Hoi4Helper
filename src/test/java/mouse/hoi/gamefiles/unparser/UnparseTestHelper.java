package mouse.hoi.gamefiles.unparser;

import mouse.hoi.gamefiles.parser.parse.FileFormatter;
import mouse.hoi.gamefiles.parser.parse.SimpleFileFormatter;
import mouse.hoi.gamefiles.parser.parse.TokenCollection;
import mouse.hoi.lib.filemanager.FileManager;
import mouse.hoi.lib.filemanager.FileManagerImpl;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnparseTestHelper {
    public void compareTokens(TokenCollection origin, TokenCollection after) {
        List<String> originTokens = origin.getAll();
        List<String> allTokens = after.getAll();
        compareLists(originTokens, allTokens);
    }

    private void compareLists(List<String> originTokens, List<String> afterTokens) {
        assertEquals(originTokens.size(), afterTokens.size());
        HashMap<Object, Integer> frequencyMap = new HashMap<>();

        for (Object element : originTokens) {
            frequencyMap.put(element, frequencyMap.getOrDefault(element, 0) + 1);
        }

        for (Object element : afterTokens) {
            assertTrue(frequencyMap.containsKey(element), "No token found in AfterTokens: " + element);
            frequencyMap.put(element, frequencyMap.get(element) - 1);
            if (frequencyMap.get(element) == 0) {
                frequencyMap.remove(element);
            }
        }

        assertTrue(frequencyMap.isEmpty(), "Not matching tokens: " + frequencyMap.keySet());
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
