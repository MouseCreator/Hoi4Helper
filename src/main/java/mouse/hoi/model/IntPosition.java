package mouse.hoi.model;

import lombok.Data;
import mouse.hoi.parser.annotation.*;
import mouse.hoi.parser.style.PrintStyle;

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
