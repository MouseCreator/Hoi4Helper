package mouse.hoi.factory;

import mouse.hoi.model.texture.SpriteType;
import mouse.hoi.model.texture.SpriteTypes;
import mouse.hoi.parser.annotation.Factory;
import mouse.hoi.parser.annotation.FactoryFor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Factory
@Component
public class SpriteTypeFactory {
    @FactoryFor
    public SpriteType getSpriteType() {
        return new SpriteType();
    }

    @FactoryFor
    public SpriteTypes getSpriteTypes() {
        SpriteTypes spriteTypes = new SpriteTypes();
        spriteTypes.setSpriteTypes(new ArrayList<>());
        return spriteTypes;
    }
}
