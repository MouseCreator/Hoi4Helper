package mouse.hoi.gamefiles.factory;

import mouse.hoi.gamefiles.tempmodel.extras.position.DoublePosition;
import mouse.hoi.gamefiles.tempmodel.extras.position.IntPosition;
import mouse.hoi.gamefiles.common.annotation.Factory;
import mouse.hoi.gamefiles.common.annotation.FactoryFor;
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
