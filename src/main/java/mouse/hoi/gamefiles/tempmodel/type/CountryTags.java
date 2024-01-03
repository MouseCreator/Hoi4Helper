package mouse.hoi.gamefiles.tempmodel.type;

import mouse.hoi.gamefiles.common.annotation.UseQuotes;
import mouse.hoi.gamefiles.tempmodel.common.SimpleMap;

import java.util.HashMap;
import java.util.Map;

public class CountryTags implements SimpleMap<String, String> {
    @UseQuotes
    private HashMap<String, String> tagToFileMap;

    @Override
    public Map<String, String> getMap() {
        return tagToFileMap;
    }
}
