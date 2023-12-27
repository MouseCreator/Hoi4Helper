package mouse.hoi.factory;

import mouse.hoi.model.texture.SpriteTypes;
import org.springframework.stereotype.Component;

@Component
public class SpriteTypesFactory implements SimpleModelFactory {
    @Override
    public SpriteTypes get() {
        return new SpriteTypes();
    }

    @Override
    public Class<?> getModelClass() {
        return SpriteTypes.class;
    }
}
