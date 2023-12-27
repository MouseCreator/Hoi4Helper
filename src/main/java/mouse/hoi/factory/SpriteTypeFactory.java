package mouse.hoi.factory;

import mouse.hoi.model.texture.SpriteType;
import org.springframework.stereotype.Component;

@Component
public class SpriteTypeFactory implements SimpleModelFactory {
    @Override
    public SpriteType get() {
        return new SpriteType();
    }

    @Override
    public Class<?> getModelClass() {
        return SpriteType.class;
    }
}
