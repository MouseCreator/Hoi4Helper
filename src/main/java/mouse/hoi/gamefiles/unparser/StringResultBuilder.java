package mouse.hoi.gamefiles.unparser;

public class StringResultBuilder {
    private int tabulationLevel;
    private final StringBuilder builder;
    private boolean isAfterNewline;
    public StringResultBuilder() {
        tabulationLevel = 0;
        builder = new StringBuilder();
        isAfterNewline = false;
    }

    public StringResultBuilder append(Object o) {
        if (isAfterNewline) {
            isAfterNewline = false;
            builder.append("\t".repeat(tabulationLevel));
        }
        builder.append(o);
        return this;
    }

    public StringResultBuilder newline() {
        isAfterNewline = true;
        builder.append("\n");
        return this;
    }

    public String get() {
        return builder.toString();
    }

    public StringResultBuilder space() {
        return append(" ");
    }
    public StringResultBuilder equality() {
        return append(" = ");
    }

    public StringResultBuilder openBlock() {
        tabulationLevel++;
        return append(" = {");
    }

    public StringResultBuilder closeBlock() {
        tabulationLevel--;
        append("}");
        return newline();
    }

}
