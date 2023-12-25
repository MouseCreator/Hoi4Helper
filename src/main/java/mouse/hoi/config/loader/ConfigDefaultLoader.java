package mouse.hoi.config.loader;


public class ConfigDefaultLoader implements ConfigLoader {
    @Override
    public String configBaseFile() {
        return "src/main/resources/mod.xml";
    }
}
