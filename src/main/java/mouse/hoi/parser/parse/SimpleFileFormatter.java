package mouse.hoi.parser.parse;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SimpleFileFormatter implements FileFormatter {

    public String formatFileContent(String content) {
        return formatFileContent(toLines(content));
    }
    public String formatFileContent(List<String> lines) {
        String result = removeCommentsAndMerge(lines);
        return removeWhitespaces(result);
    }
    public List<String> toLines(String content) {
        List<String> lines = new ArrayList<>();
        if (content != null && !content.isEmpty()) {
            String[] lineArray = content.split("\\r?\\n");
            Collections.addAll(lines, lineArray);
        }
        return lines;
    }

    @Override
    public List<String> toTokens(String input) {
        List<String> splitList = new ArrayList<>();

        Pattern pattern = Pattern.compile("\"(.*?)\"|\\S+");
        Matcher matcher = pattern.matcher(input);
        boolean insideQuotes = true;
        while (matcher.find()) {
            String match = matcher.group();
            insideQuotes = !insideQuotes;
            if (match.startsWith("\"") && match.endsWith("\"") && insideQuotes) {
                match = match.substring(1, match.length() - 1);
            }
            splitList.add(match);
        }

        return splitList;
    }

    private String inQuotes(String str) {
        return '"' + str + '"';
    }

    @Override
    public List<String> formatAndTokenize(String content) {
        return toTokens(formatFileContent(content));
    }

    private String removeCommentsAndMerge(List<String> lines) {
        StringBuilder builder = new StringBuilder();
        for (String line : lines) {
            if (line.isEmpty())
                continue;
            String noComment = removeComment(line);
            if (noComment.isEmpty())
                continue;
            builder.append(noComment);
            builder.append("\n");
        }
        return builder.toString();
    }

    private String removeComment(String line) {
        if (line.startsWith("#")) {
            return "";
        }
        String[] split = line.split("#", 2);
        if (split.length == 1) {
            return line;
        }
        assert split.length == 2;

        return split[0];
    }
    private String removeWhitespaces(String content) {
        List<String> insideQuotes = new ArrayList<>();
        List<String> outsideQuotes = new ArrayList<>();

        Pattern pattern = Pattern.compile("\"(.*?)\"");
        Matcher matcher = pattern.matcher(content);

        int start = 0;

        while (matcher.find()) {
            String outsideText = content.substring(start, matcher.start());
            outsideQuotes.add(formatWhiteSpaces(outsideText));

            String insideText = matcher.group(1);
            insideQuotes.add(insideText);

            start = matcher.end();
        }

        if (start < content.length()) {
            String outsideText = formatWhiteSpaces(content.substring(start));
            outsideQuotes.add(outsideText);
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < Math.max(insideQuotes.size(), outsideQuotes.size()); i++) {
            if (i < outsideQuotes.size()) {
                result.append(outsideQuotes.get(i));
            }
            if (i < insideQuotes.size()) {
                result.append("\"").append(insideQuotes.get(i)).append("\"");
            }
        }

        return result.toString().trim();
    }

    private static String formatWhiteSpaces(String outsideText) {
        outsideText = outsideText.replaceAll("\t", " ");
        outsideText = outsideText.replaceAll("\n", " ");
        outsideText = outsideText.replaceAll("=", " = ");
        outsideText = outsideText.replaceAll("\\{", " { ");
        outsideText = outsideText.replaceAll("}", " } ");
        outsideText = outsideText.replaceAll(" +", " ");
        return outsideText;
    }
}
