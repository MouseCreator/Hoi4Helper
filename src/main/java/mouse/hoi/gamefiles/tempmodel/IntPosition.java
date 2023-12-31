package mouse.hoi.gamefiles.tempmodel;

import lombok.Data;
import mouse.hoi.gamefiles.common.annotation.Block;
import mouse.hoi.gamefiles.common.annotation.ObjField;
import mouse.hoi.gamefiles.common.annotation.RequireField;
import mouse.hoi.gamefiles.common.annotation.Style;
import mouse.hoi.gamefiles.unparser.style.PrintStyle;

@Data
@Block(name = "position")
@Style(printStyle = PrintStyle.SIMPLE)
public class IntPosition {

    @RequireField
    @ObjField(text = "x")
    private int x;

    @RequireField
    @ObjField(text = "y")
    private int y;


    public static IntPosition zeros() {
        IntPosition position = new IntPosition();
        position.x = 0;
        position.y = 0;
        return position;
    }
}
