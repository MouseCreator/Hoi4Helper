package mouse.hoi.unparser;

public class ResultBuilder {
    private final StringBuilder stringBuilder;
    private int tabulationLevel;
    private boolean isAfterNewline = false;

    public ResultBuilder() {
        stringBuilder = new StringBuilder();
        tabulationLevel = 0;
    }

    public ResultBuilder append(Object o) {
        if (isAfterNewline) {
            stringBuilder.append("\t".repeat(tabulationLevel));
        }
        stringBuilder.append(o);
        return this;
    }

    public String get() {
        return stringBuilder.toString();
    }
    public ResultBuilder newline() {
        stringBuilder.append("\n");
        isAfterNewline = true;
        return this;
    }
    public ResultBuilder space() {
        stringBuilder.append(" ");
        return this;
    }
    public ResultBuilder openBlock() {
        stringBuilder.append(" = {");
        tabulationLevel++;
        return this;
    }
    public ResultBuilder closeBlock() {
        stringBuilder.append("}");
        tabulationLevel--;
        return newline();
    }

    public ResultBuilder equality() {
        stringBuilder.append(" = ");
        return this;
    }
}
