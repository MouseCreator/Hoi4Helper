package mouse.hoi.model.texture;

import lombok.Data;
import mouse.hoi.model.common.SimpleCollection;
import mouse.hoi.parser.annotation.Block;
import mouse.hoi.parser.annotation.ObjField;

import java.util.Collection;
import java.util.List;
@Data
@Block(name = "spriteTypes")
public class SpriteTypes implements SimpleCollection<SpriteType> {

    @ObjField(text = "SpriteType")
    List<SpriteType> spriteTypes;

    @Override
    public Collection<SpriteType> getCollection() {
        return spriteTypes;
    }
}
