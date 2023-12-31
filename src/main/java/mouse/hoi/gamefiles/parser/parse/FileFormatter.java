package mouse.hoi.gamefiles.parser.parse;

import java.util.List;

public interface FileFormatter {
    String formatFileContent(String content);
    String formatFileContent(List<String> lines);
    List<String> toLines(String content);
    List<String> toTokens(String input);
    List<String> formatAndTokenize(String content);
}
