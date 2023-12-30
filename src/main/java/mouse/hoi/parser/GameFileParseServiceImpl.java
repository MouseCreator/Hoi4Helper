package mouse.hoi.parser;

import mouse.hoi.lib.filemanager.FileManager;
import mouse.hoi.parser.parse.FileFormatter;
import mouse.hoi.parser.parse.PropertyParser;
import mouse.hoi.parser.parse.TokenCollection;
import mouse.hoi.parser.property.input.Property;
import mouse.hoi.parser.result.ParsingResult;
import mouse.hoi.parser.result.ParsingResultImpl;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GameFileParseServiceImpl implements GameFileParseService {
    private final GameFileParser gameFileParser;
    private final PropertyParser propertyParser;
    private final FileFormatter fileFormatter;
    private final FileManager fileManager;
    public GameFileParseServiceImpl(GameFileParser gameFileParser,
                                    PropertyParser propertyParser,
                                    FileFormatter fileFormatter,
                                    FileManager fileManager) {
        this.gameFileParser = gameFileParser;
        this.propertyParser = propertyParser;
        this.fileFormatter = fileFormatter;
        this.fileManager = fileManager;
    }

    @Override
    public <T> ParsingResult<T> parseAndGet(Class<T> clazz, String filename) {
        String content = fileManager.read(filename);
        List<Property> properties = propertyParser.parse(new TokenCollection(fileFormatter.formatAndTokenize(content)));
        List<T> parsingResult = gameFileParser.parseFrom(clazz, properties);
        return new ParsingResultImpl<>(parsingResult);
    }
}
