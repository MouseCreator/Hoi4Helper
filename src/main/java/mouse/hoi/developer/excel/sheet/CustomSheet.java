package mouse.hoi.developer.excel.sheet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomSheet implements Iterable<Row> {
    private final List<Row> rows;

    public CustomSheet() {
        this.rows = new ArrayList<>();
    }

    public String getAt(int row, int col) {
        return rows.get(row).get(col).getContent();
    }

    public void appendRow(Row newRow) {
        rows.add(newRow);
    }

    public int nRows() {
        return rows.size();
    }
    public int nCols() {
        return nRows() == 0 ? 0 : rows.get(0).size();
    }

    @Override
    public Iterator<Row> iterator() {
        return rows.iterator();
    }
}
