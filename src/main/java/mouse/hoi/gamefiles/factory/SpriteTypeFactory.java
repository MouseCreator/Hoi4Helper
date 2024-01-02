package mouse.hoi.gamefiles.factory;

import mouse.hoi.gamefiles.tempmodel.texture.SpriteType;
import mouse.hoi.gamefiles.tempmodel.texture.SpriteTypes;
import mouse.hoi.gamefiles.common.annotation.Factory;
import mouse.hoi.gamefiles.common.annotation.FactoryFor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Factory
@Component
public class SpriteTypeFactory {
    @FactoryFor
    public SpriteType getSpriteType() {
        SpriteType spriteType = new SpriteType();
        spriteType.setAnimationList(new ArrayList<>());
        spriteType.setLazyLoad(true);
        return spriteType;
    }

    @FactoryFor
    public SpriteTypes getSpriteTypes() {
        SpriteTypes spriteTypes = new SpriteTypes();
        spriteTypes.setSpriteTypes(new ArrayList<>());
        return spriteTypes;
    }
}
