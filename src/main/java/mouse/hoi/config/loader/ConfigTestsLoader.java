package mouse.hoi.config.loader;


public class ConfigTestsLoader implements ConfigLoader{
    @Override
    public String configBaseFile() {
        return "src/test/resources/mod.xml";
    }
}
