package mouse.hoi.gamefiles.tempmodel.texture;

import lombok.Data;
import mouse.hoi.gamefiles.tempmodel.DoublePosition;
import mouse.hoi.gamefiles.common.annotation.Accuracy;
import mouse.hoi.gamefiles.common.annotation.Block;
import mouse.hoi.gamefiles.common.annotation.ObjField;
import mouse.hoi.gamefiles.common.annotation.UseQuotes;

@Data
@Block(name = "animation")
public class Animation {
    @ObjField(text = "animationmaskfile")
    @UseQuotes
    private String maskFile;

    @ObjField(text = "animationtexturefile")
    @UseQuotes
    private String textureFile;

    @Accuracy(digits = 1)
    @ObjField(text = "animationrotation")
    private Double rotation;

    @ObjField(text = "animationlooping")
    private Boolean looping;

    @ObjField(text = "animationtime")
    @Accuracy(digits = 2)
    private Double timeSeconds;

    @ObjField(text = "animationdelay")
    @Accuracy(digits = 2)
    private Double delaySeconds;

    @ObjField(text = "animationblendmode")
    @UseQuotes
    private String blendMode;

    @ObjField(text = "animationtype")
    @UseQuotes
    private String type;

    @ObjField(text = "animationrotationoffset")
    private DoublePosition rotationOffset;

    @ObjField(text = "animationtexturescale")
    private DoublePosition textureScale;
}
