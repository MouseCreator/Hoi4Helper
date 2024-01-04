package mouse.hoi.gamefiles.tempmodel.dynamic;

import lombok.Data;
import mouse.hoi.gamefiles.common.annotation.AnyKey;
import mouse.hoi.gamefiles.common.annotation.Block;
import mouse.hoi.gamefiles.common.annotation.FromBlockValue;
import mouse.hoi.gamefiles.common.annotation.FromKeyValue;

@Data
@Block
public class Trigger {
    @FromKeyValue
    private String key;
    @FromBlockValue
    private String value;
    @AnyKey
    private Triggers children;

}
