package mouse.hoi.developer.excel.sheet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Row implements Iterable<Cell> {
    private final List<Cell> cells;
    public Row() {
        cells = new ArrayList<>();
    }

    public Cell get(int col) {
        return cells.get(col);
    }
    public int size() {
        return cells.size();
    }

    public void add(String value) {
        cells.add(new Cell(value));
    }

    @Override
    public Iterator<Cell> iterator() {
        return cells.iterator();
    }
}
