package mouse.hoi.developer.excel.sheet;

public class SheetBuilder {
    private CustomSheet sheet;

    private Row currentRow;

    public SheetBuilder() {
        this.sheet = new CustomSheet();
        currentRow = new Row();
    }

    public CustomSheet get() {
        return sheet;
    }
    public void insert(Object value) {
        String s = value.toString();
        currentRow.add(s);
    }
    public void nextRow() {
        sheet.appendRow(currentRow);
        currentRow = new Row();
    }
}
