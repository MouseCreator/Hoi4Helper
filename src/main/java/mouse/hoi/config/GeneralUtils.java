package mouse.hoi.config;

import mouse.hoi.config.fillable.ModData;
import mouse.hoi.config.loader.ConfigLoader;
import mouse.hoi.lib.xml.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class GeneralUtils {
    private ModData modData;

    private final Parser parser;

    private final ConfigLoader configLoader;
    @Autowired
    public GeneralUtils(Parser parser, ConfigLoader configLoader) {
        this.parser = parser;
        this.configLoader = configLoader;
    }

    @PostConstruct
    void init() {
        modData = (ModData) parser.parse(configLoader.configBaseFile());
    }

    public ModData getModData() {
        return modData;
    }
}
