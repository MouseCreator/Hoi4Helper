package mouse.hoi.developer.excel.sheet;

public class Cell {
    private String content;


    public Cell(String content) {
        this.content = content;
    }
    public Cell() {
        this.content = "";
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
