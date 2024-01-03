package mouse.hoi.gamefiles.tempmodel.common.countrytags;

import lombok.Data;
import mouse.hoi.gamefiles.common.annotation.AnyKey;
import mouse.hoi.gamefiles.common.annotation.SkipDeclaration;
import mouse.hoi.gamefiles.common.annotation.UseQuotes;
import mouse.hoi.gamefiles.tempmodel.extras.SimpleMap;

import java.util.HashMap;
import java.util.Map;
@Data
@SkipDeclaration
public class CountryTags implements SimpleMap<String, String> {
    @UseQuotes
    @AnyKey
    private HashMap<String, String> tagToFileMap;

    @Override
    public Map<String, String> getMap() {
        return tagToFileMap;
    }
}
