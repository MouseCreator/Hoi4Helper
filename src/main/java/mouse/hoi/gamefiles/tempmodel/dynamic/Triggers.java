package mouse.hoi.gamefiles.tempmodel.dynamic;

import lombok.Data;
import mouse.hoi.gamefiles.common.annotation.Block;
import mouse.hoi.gamefiles.common.annotation.CollectionKey;
import mouse.hoi.gamefiles.common.annotation.ObjField;
import mouse.hoi.gamefiles.common.annotation.SkipDeclaration;
import mouse.hoi.gamefiles.common.collectiontype.CollectionType;

import java.util.List;

@Block
@SkipDeclaration
@Data
public class Triggers {
    @ObjField(text = "AND")
    private Triggers andTriggers;
    @ObjField(text = "OR")
    private Triggers orTriggers;
    @CollectionKey(type = CollectionType.TAG)
    private List<Trigger> countryTriggers;
    @CollectionKey(type = CollectionType.STATE_ID)
    private List<Trigger> stateTriggers;
    @CollectionKey(type = CollectionType.TRIGGER)
    private List<Trigger> triggers;
}
