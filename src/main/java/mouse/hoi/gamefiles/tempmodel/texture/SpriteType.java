package mouse.hoi.gamefiles.tempmodel.texture;

import lombok.Data;
import mouse.hoi.gamefiles.common.annotation.Block;
import mouse.hoi.gamefiles.common.annotation.ObjField;
import mouse.hoi.gamefiles.common.annotation.UseQuotes;

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
