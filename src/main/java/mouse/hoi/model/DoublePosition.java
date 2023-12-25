package mouse.hoi.model;

import lombok.Data;

@Data
public class DoublePosition {

    public static DoublePosition get(double x, double y) {
        DoublePosition doublePosition = new DoublePosition();
        doublePosition.x = x;
        doublePosition.y = y;
        return doublePosition;
    }

    private double x;
    private double y;

    public static DoublePosition zeros() {
        return get(0,0);
    }
    public static DoublePosition ones() {
        return get(1,1);
    }
}
