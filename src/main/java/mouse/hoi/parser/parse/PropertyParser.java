package mouse.hoi.parser.parse;

public class PropertyParser {
    public boolean nextIsEqualSign(String token) {
        return token.equals("=");
    }

    public boolean nextOpensBlock(String token) {
        return token.equals("{");
    }

    public boolean nextClosesBlock(String token) {
        return token.equals("}");
    }

    public boolean nextIsRegularToken(String token) {
        return startsWithRegularCharacter(token);
    }

    public boolean startsWithRegularCharacter(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        char firstChar = token.charAt(0);
        return (firstChar >= 'A' && firstChar <= 'Z') ||
                (firstChar >= 'a' && firstChar <= 'z') ||
                (firstChar >= '0' && firstChar <= '9') ||
                (firstChar == '_');
    }
}
