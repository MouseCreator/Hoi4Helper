package mouse.hoi.model.texture;

import lombok.Data;
import mouse.hoi.parser.annotation.Block;
import mouse.hoi.parser.annotation.ObjField;
import mouse.hoi.parser.annotation.UseQuotes;

import java.util.List;

@Data
@Block(name = "SpriteType")
public class SpriteType {

    @ObjField(text = "name")
    @UseQuotes
    private String name;
    @ObjField(text = "texturefile")
    @UseQuotes
    private String textureFile;
    @ObjField(text = "effectFile")
    @UseQuotes
    private String effectFile;
    @ObjField(text = "legacy_lazy_load")
    private Boolean lazyLoad;
    @ObjField(text = "animation")
    private List<Animation> animationList;
}
