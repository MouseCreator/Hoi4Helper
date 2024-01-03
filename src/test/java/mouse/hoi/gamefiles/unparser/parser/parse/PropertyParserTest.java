package mouse.hoi.gamefiles.unparser.parser.parse;

import mouse.hoi.gamefiles.parser.parse.FileFormatter;
import mouse.hoi.gamefiles.parser.parse.PropertyParser;
import mouse.hoi.gamefiles.parser.parse.SimpleFileFormatter;
import mouse.hoi.gamefiles.parser.parse.TokenCollection;
import mouse.hoi.gamefiles.parser.property.Property;
import mouse.hoi.gamefiles.parser.propertyfactory.PropertyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PropertyParserTest {


    private PropertyParser parser;

    @BeforeEach
    void setUp() {
        parser = new PropertyParser(new PropertyFactory());
    }
    @Test
    void parse() {
        String sampleString = """
                a = {
                    c = "GFX_"
                    d = { e = { } }
                }
                
                
                b = {
                    f = 0.05
                    g = "hello"
                }
                """;
        FileFormatter fileFormatter = new SimpleFileFormatter();
        List<String> strings = fileFormatter.formatAndTokenize(sampleString);
        TokenCollection tokenCollection = new TokenCollection(strings);
        List<Property> parsed = parser.parse(tokenCollection);
        assertEquals(2, parsed.size());
        assertEquals("a={c=\"GFX_\";d={e={}}}", parsed.get(0).print());
        assertEquals("b={f=0.05;g=\"hello\";}", parsed.get(1).print());
    }
}