package mouse.hoi.parser.result;

import java.util.List;

public interface ParsingResult<T> {
    T getFirst();
    List<T> getAll();
    boolean isSingle();
    boolean isEmpty();
    int size();
}
