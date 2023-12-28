package mouse.hoi.factory;

import mouse.hoi.model.DoublePosition;
import mouse.hoi.model.IntPosition;
import mouse.hoi.parser.annotation.Factory;
import mouse.hoi.parser.annotation.FactoryFor;
import org.springframework.stereotype.Component;

@Component
@Factory
public class PositionFactory {
    @FactoryFor
    public DoublePosition getDoublePosition() {
        return DoublePosition.zeros();
    }

    @FactoryFor
    public IntPosition getIntPosition() {
        return IntPosition.zeros();
    }
}
