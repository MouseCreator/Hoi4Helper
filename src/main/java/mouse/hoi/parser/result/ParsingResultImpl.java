package mouse.hoi.parser.result;

import java.util.ArrayList;
import java.util.List;

public class ParsingResultImpl<T> implements ParsingResult<T> {

    private final List<T> resultList;

    public ParsingResultImpl(List<T> resultList) {
        this.resultList = resultList;
    }

    @Override
    public T getFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("Getting from empty parsing result");
        }
        return resultList.get(0);
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(resultList);
    }

    @Override
    public boolean isSingle() {
        return size()==1;
    }

    @Override
    public boolean isEmpty() {
        return resultList.isEmpty();
    }

    @Override
    public int size() {
        return resultList.size();
    }
}
