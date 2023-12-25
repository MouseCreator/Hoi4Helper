package mouse.hoi.model.texture;

import lombok.Data;
import mouse.hoi.model.DoublePosition;
import mouse.hoi.parser.annotation.Accuracy;
import mouse.hoi.parser.annotation.Block;
import mouse.hoi.parser.annotation.Field;
import mouse.hoi.parser.annotation.UseBrackets;

@Data
@Block(name = "animation")
public class Animation {
    @Field(text = "animationmaskfile")
    @UseBrackets
    private String maskFile;

    @Field(text = "animationtexturefile")
    @UseBrackets
    private String textureFile;

    @Accuracy(digits = 1)
    @Field(text = "animationrotation")
    private Double rotation;

    @Field(text = "animationlooping")
    private Boolean looping;

    @Field(text = "animationtime")
    @Accuracy(digits = 2)
    private Double timeSeconds;

    @Field(text = "animationdelay")
    @Accuracy(digits = 2)
    private Double delaySeconds;

    @Field(text = "animationblendmode")
    @UseBrackets
    private String blendMode;

    @Field(text = "animationtype")
    @UseBrackets
    private String type;

    @Field(text = "animationrotationoffset")
    private DoublePosition rotationOffset;

    @Field(text = "animationtexturescale")
    private DoublePosition textureScale;
}
