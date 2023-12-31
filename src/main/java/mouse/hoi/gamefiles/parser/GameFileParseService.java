package mouse.hoi.gamefiles.parser;

import mouse.hoi.gamefiles.parser.result.ParsingResult;

public interface GameFileParseService {
    <T> ParsingResult<T> parseAndGet(Class<T> clazz, String filename);
}
