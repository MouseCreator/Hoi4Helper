package mouse.hoi.gamefiles.parser.parse;

import java.util.ArrayList;
import java.util.List;

public class TokenCollection {
    private final List<String> tokens;
    public TokenCollection() {
        this.tokens = new ArrayList<>();
    }
    public TokenCollection(List<String> tokens) {
        this.tokens = new ArrayList<>(tokens);
    }

    public TokenCollection(TokenCollection tokenCollection) {
        this.tokens = new ArrayList<>(tokenCollection.tokens);
    }

    public void addAll(List<String> other) {
        tokens.addAll(other);
    }
    public boolean isEmpty() {
        return tokens.isEmpty();
    }

    public boolean hasNext() {
        return !isEmpty();
    }

    public void skip() {
        if (hasNext()) {
            tokens.remove(0);
        }
    }
    public void skip(int steps) {
        for (int i = 0; i < steps; i++) {
            skip();
        }
    }

    public String nextToken() {
        return tokens.remove(0);
    }


    public String look() {
        return tokens.get(0);
    }

    public void addToken(String token) {
        tokens.add(token);
    }
}
