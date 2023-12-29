package mouse.hoi.parser;

import mouse.hoi.config.spring.TestConfig;
import mouse.hoi.lib.filemanager.FileManager;
import mouse.hoi.model.texture.SpriteType;
import mouse.hoi.model.texture.SpriteTypes;
import mouse.hoi.parser.parse.FileFormatter;
import mouse.hoi.parser.parse.PropertyParser;
import mouse.hoi.parser.parse.TokenCollection;
import mouse.hoi.parser.property.Property;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameFileParserTest {

    @Test
    void parseFrom() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        GameFileParser gameFileParser = context.getBean(GameFileParser.class);
        PropertyParser propertyParser = context.getBean(PropertyParser.class);
        FileFormatter formatter = context.getBean(FileFormatter.class);
        FileManager fileManager = context.getBean(FileManager.class);
        String content = fileManager.read("src/test/resources/assets/parse/SampleSpriteTypes_01.txt");
        List<Property> properties = propertyParser.parse(new TokenCollection(formatter.formatAndTokenize(content)));
        List<SpriteTypes> spriteTypesList = gameFileParser.parseFrom(SpriteTypes.class, properties);
        assertEquals(1, spriteTypesList.size());
        SpriteTypes spriteTypes = spriteTypesList.get(0);

        List<SpriteType> spriteTypeList = spriteTypes.getSpriteTypes();
        assertEquals(2, spriteTypeList.size());
        System.out.println(spriteTypesList);
    }
}