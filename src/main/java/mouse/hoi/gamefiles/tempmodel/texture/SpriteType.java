package mouse.hoi.gamefiles.tempmodel.texture;

import lombok.Data;
import mouse.hoi.gamefiles.common.annotation.*;

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
    @OmitIfDefault
    private String effectFile;
    @ObjField(text = "legacy_lazy_load")
    @OmitIfDefault
    private Boolean lazyLoad;
    @ObjField(text = "animation")
    @OmitIfEmpty
    private List<Animation> animationList;
}
