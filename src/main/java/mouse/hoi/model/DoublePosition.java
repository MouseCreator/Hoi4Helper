package mouse.hoi.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import mouse.hoi.parser.annotation.*;
import mouse.hoi.parser.style.PrintStyle;

@Data
@Block(name = "pos")
@Style(printStyle = PrintStyle.SIMPLE)
@NoArgsConstructor
public class DoublePosition {

    public static DoublePosition get(double x, double y) {
        DoublePosition doublePosition = new DoublePosition();
        doublePosition.x = x;
        doublePosition.y = y;
        return doublePosition;
    }
    @Accuracy
    @RequireField
    @ObjField(text = "x")
    private Double x;

    @Accuracy
    @RequireField
    @ObjField(text = "y")
    private Double y;

    public static DoublePosition zeros() {
        return get(0,0);
    }
    public static DoublePosition ones() {
        return get(1,1);
    }

}
