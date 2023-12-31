package mouse.hoi.gamefiles.tempmodel.texture;

import lombok.Data;
import mouse.hoi.gamefiles.tempmodel.common.SimpleCollection;
import mouse.hoi.gamefiles.common.annotation.Block;
import mouse.hoi.gamefiles.common.annotation.ObjField;

import java.util.Collection;
import java.util.List;
@Data
@Block(name = "spriteTypes")
public class SpriteTypes implements SimpleCollection<SpriteType> {

    @ObjField(text = "SpriteType")
    private List<SpriteType> spriteTypes;

    @Override
    public Collection<SpriteType> getCollection() {
        return spriteTypes;
    }
}
