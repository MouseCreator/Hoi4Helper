package mouse.hoi.model;

import lombok.Data;
import mouse.hoi.parser.annotation.Block;
import mouse.hoi.parser.annotation.ObjField;
import mouse.hoi.parser.annotation.RequireField;
import mouse.hoi.parser.annotation.Style;
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
}
