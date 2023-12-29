package mouse.hoi.parser;

import mouse.hoi.parser.result.ParsingResult;

public interface GameFileParseService {
    <T> ParsingResult<T> parseAndGet(Class<T> clazz, String filename);
}
