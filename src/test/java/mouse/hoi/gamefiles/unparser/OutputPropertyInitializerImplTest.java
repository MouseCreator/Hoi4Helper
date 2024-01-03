package mouse.hoi.gamefiles.unparser;

import mouse.hoi.config.spring.TestConfig;
import mouse.hoi.gamefiles.parser.GameFileParseService;
import mouse.hoi.gamefiles.parser.parse.TokenCollection;
import mouse.hoi.gamefiles.parser.result.ParsingResult;
import mouse.hoi.gamefiles.tempmodel.common.resources.Resource;
import mouse.hoi.gamefiles.tempmodel.common.resources.Resources;
import mouse.hoi.gamefiles.tempmodel.interfacetexture.SpriteTypes;
import mouse.hoi.gamefiles.tempmodel.common.countrytags.CountryTags;
import mouse.hoi.gamefiles.unparser.property.OutputProperty;
import mouse.hoi.gamefiles.unparser.unparsing.PropertyToStringUnparser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

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
        runForSpriteTypes(filename, 5);
    }
    @Test
    void initializeShineSpriteTypes() {
        String filename= "src/test/resources/assets/parse/SampleSpriteTypes_01.txt";
        runForSpriteTypes(filename, 2);
    }

    void runForSpriteTypes(String file, int expectedElements) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        GameFileParseService parser = context.getBean(GameFileParseService.class);

        ParsingResult<SpriteTypes> spriteTypesParsingResult = parser.parseAndGet(SpriteTypes.class, file);
        assertEquals(1, spriteTypesParsingResult.size());
        SpriteTypes instance = spriteTypesParsingResult.getFirst();

        assertEquals(expectedElements, instance.getSpriteTypes().size());
        unparseAndcompareWithInitial(instance, context, file);
    }

    private void unparseAndcompareWithInitial(Object instance, AnnotationConfigApplicationContext context, String file) {
        OutputPropertyInitializer unparser = context.getBean(OutputPropertyInitializer.class);
        List<OutputProperty> properties = unparser.initializeProperty(instance);
        PropertyToStringUnparser propertyUnparser = context.getBean(PropertyToStringUnparser.class);
        String unparsedContent = propertyUnparser.unparse(properties);
        TokenCollection tokensBefore = unparseTestHelper.tokenize(file);
        TokenCollection tokensAfter = unparseTestHelper.tokenizeContent(unparsedContent);

        unparseTestHelper.compareTokens(tokensBefore, tokensAfter);
    }

    @Test
    void initializeCountryTagsWithMap() {
        String filename= "src/test/resources/assets/parse/SampleCountryTags_01.txt";
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        GameFileParseService parser = context.getBean(GameFileParseService.class);

        ParsingResult<CountryTags> result = parser.parseAndGet(CountryTags.class, filename);
        assertEquals(1, result.size());
        CountryTags countryTags = result.getFirst();
        Map<String, String> resultMap = countryTags.getMap();
        assertEquals(15, resultMap.size());

        unparseAndcompareWithInitial(countryTags, context, filename);

    }

    @Test
    void initializeResources() {
        String filename= "src/test/resources/assets/parse/SampleResources_01.txt";
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        GameFileParseService parser = context.getBean(GameFileParseService.class);

        ParsingResult<Resources> result = parser.parseAndGet(Resources.class, filename);
        assertEquals(1, result.size());
        Resources resources = result.getFirst();
        List<Resource> resourceList = resources.getResourceList();
        assertEquals(6, resourceList.size());

        unparseAndcompareWithInitial(resources, context, filename);

    }
}