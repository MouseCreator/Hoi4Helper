package mouse.hoi.model.texture;

import lombok.Data;
import mouse.hoi.parser.annotation.Block;
import mouse.hoi.parser.annotation.ObjField;

import java.util.List;
@Data
@Block(name = "spriteTypes")
public class SpriteTypes {

    @ObjField(text = "SpriteType")
    List<SpriteType> spriteTypes;
}
