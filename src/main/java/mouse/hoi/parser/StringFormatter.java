package mouse.hoi.parser;

import org.springframework.stereotype.Component;

@Component
public class StringFormatter {
    public String removeQuotes(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        if (input.startsWith("\"") && input.endsWith("\"")) {
            return input.substring(1, input.length() - 1);
        }

        return input;

    }
}
