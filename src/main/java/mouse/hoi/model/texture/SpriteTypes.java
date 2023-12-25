package mouse.hoi.model.texture;

import lombok.Data;
import mouse.hoi.parser.annotation.Block;
import mouse.hoi.parser.annotation.Field;

import java.util.List;
@Data
@Block(name = "SpriteTypes")
public class SpriteTypes {

    @Field(text = "SpriteType")
    List<SpriteType> spriteTypes;
}
