package mouse.hoi.developer.searcher;


import mouse.hoi.gamefiles.parser.parse.FileFormatter;
import mouse.hoi.gamefiles.parser.parse.PropertyParser;
import mouse.hoi.gamefiles.parser.parse.TokenCollection;
import mouse.hoi.gamefiles.parser.property.Property;
import mouse.hoi.lib.filemanager.FileManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
@Service
public class ScopedSeeker {
    private final PropertyParser propertyParser;
    private final FileFormatter formatter;
    private final FileManager fileManager;
    @Autowired
    public ScopedSeeker(PropertyParser propertyParser, FileFormatter formatter, FileManager fileManager) {
        this.propertyParser = propertyParser;
        this.formatter = formatter;
        this.fileManager = fileManager;
    }

    public List<Property> lookForTriggerDeclarations(String root) {
        FileSearcher searcher = new FileSearcher();
        List<String> txtFiles = searcher.findTxtFiles(root);
        List<Property> properties = new ArrayList<>();

        for (String file : txtFiles) {
            String content = fileManager.read(file);
            try {
                List<Property> sub = propertyParser.parse(new TokenCollection(formatter.formatAndTokenize(content)));
                properties.addAll(sub);
            } catch (Exception e) {
                System.out.println("Error parsing " + file + ":\n");
                e.printStackTrace();
            }
        }

        return toUniqueKey(search(properties));
    }

    private List<Property> search(List<Property> propertyList) {
        return SeekerResultBuilder.seek(propertyList).under(anyOf("if", "else_if")).under("limit")
                .dive(toDive()).remove(s -> s.matches("[+-]?\\d+")).get();
    }

    private List<Property> toUniqueKey(List<Property> properties) {
        Map<String, Property> uniqueKeyMap = properties.stream()
                .collect(Collectors.toMap(Property::getKey, p -> p, (p1, p2) -> p1));
        return new ArrayList<>(uniqueKeyMap.values());
    }

    private Predicate<String> toDive() {
        return s -> anyOf("FROM", "TO", "ROOT", "PREV", "AND", "OR", "OVERLORD",
                "any_other_country").test(s) || s.matches("^[A-Z]{3}$");
    }

    private Predicate<String> anyOf(String... tokens) {
        return s -> {
          for (String token : tokens) {
              if (token.equals(s)) {
                  return true;
              }
          }
          return false;
        };
    }
}
