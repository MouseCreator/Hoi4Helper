package mouse.hoi.gamefiles.tempmodel.texture;

import lombok.Data;
import mouse.hoi.gamefiles.common.annotation.*;

import java.util.List;

@Data
@Block(name = "SpriteType")
public class SpriteType {

    @ObjField(text = "name")
    @Priority(10)
    @UseQuotes
    private String name;
    @ObjField(text = "texturefile")
    @Priority(9)
    @UseQuotes
    private String textureFile;
    @ObjField(text = "effectFile")
    @UseQuotes
    @OmitIfAsInDefault
    @Priority(8)
    private String effectFile;
    @ObjField(text = "legacy_lazy_load")
    @OmitIfAsInDefault
    @Priority(-1)
    private Boolean lazyLoad;
    @ObjField(text = "animation")
    private List<Animation> animationList;
}
