package mouse.hoi.gamefiles.unparser;

import mouse.hoi.config.spring.TestConfig;
import mouse.hoi.gamefiles.parser.GameFileParseService;
import mouse.hoi.gamefiles.parser.parse.TokenCollection;
import mouse.hoi.gamefiles.parser.result.ParsingResult;
import mouse.hoi.gamefiles.tempmodel.texture.SpriteTypes;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.unparsing.PropertyToStringUnparser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OutputPropertyInitializerImplTest {
    private UnparseTestHelper unparseTestHelper;
    @BeforeEach
    void init() {
        unparseTestHelper = new UnparseTestHelper();
    }

    @Test
    void initializeGoalSpriteTypes() {
        String filename= "src/test/resources/assets/parse/SampleSpriteTypes_02.txt";
        runFor(filename, 5);
    }
    @Test
    void initializeShineSpriteTypes() {
        String filename= "src/test/resources/assets/parse/SampleSpriteTypes_01.txt";
        runFor(filename, 2);
    }

    void runFor(String file, int expectedElements) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        GameFileParseService parser = context.getBean(GameFileParseService.class);

        ParsingResult<SpriteTypes> spriteTypesParsingResult = parser.parseAndGet(SpriteTypes.class, file);
        assertEquals(1, spriteTypesParsingResult.size());
        SpriteTypes instance = spriteTypesParsingResult.getFirst();

        assertEquals(expectedElements, instance.getSpriteTypes().size());
        OutputPropertyInitializer unparser = context.getBean(OutputPropertyInitializer.class);

        List<OutputProperty> properties = unparser.initializeProperty(instance);
        PropertyToStringUnparser propertyUnparser = context.getBean(PropertyToStringUnparser.class);
        String unparsedContent = propertyUnparser.unparse(properties);

        TokenCollection tokensBefore = unparseTestHelper.tokenize(file);
        TokenCollection tokensAfter = unparseTestHelper.tokenizeContent(unparsedContent);

        unparseTestHelper.compareTokens(tokensBefore, tokensAfter);
    }
}