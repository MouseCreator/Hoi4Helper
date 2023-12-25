package mouse.hoi.lib.xml.fill;

public interface Filler {
    <T> void fill(Object toInitialize, String attribute, T value);
}
