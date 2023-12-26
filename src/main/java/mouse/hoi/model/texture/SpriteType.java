package mouse.hoi.model.texture;

import lombok.Data;
import mouse.hoi.parser.annotation.Block;
import mouse.hoi.parser.annotation.Field;
import mouse.hoi.parser.annotation.UseBrackets;

import java.util.List;

@Data
@Block(name = "SpriteType")
public class SpriteType {

    @Field(text = "name")
    @UseBrackets
    private String name;
    @Field(text = "texturefile")
    @UseBrackets
    private String textureFile;
    @Field(text = "effectFile")
    @UseBrackets
    private String effectFile;
    @Field(text = "legacy_lazy_load")
    private Boolean lazyLoad;
    @Field(text = "animation")
    private List<Animation> animationList;
}
